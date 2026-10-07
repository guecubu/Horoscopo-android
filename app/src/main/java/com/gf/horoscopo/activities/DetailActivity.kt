package com.gf.horoscopo.activities

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.gf.horoscopo.R
import com.gf.horoscopo.data.Horoscope
import com.gf.horoscopo.data.HoroscopeApiService
import com.gf.horoscopo.utils.SessionManager
import com.gf.horoscopo.utils.setTranslatedText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : androidx.appcompat.app.AppCompatActivity() {

    private lateinit var iconView: ImageView
    private lateinit var horoscopeName: TextView
    private lateinit var horoscopeDescription: TextView
    private lateinit var horoscopeDate: TextView
    private lateinit var horoscopeProgressBar: ProgressBar

    private lateinit var session: SessionManager
    private var isFavorite = false
    private lateinit var horoscope: Horoscope
    private lateinit var favoriteMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        session = SessionManager(this)

        val id = intent.getStringExtra("HOROSCOPE_ID")!!
        isFavorite = session.isFavorite(id)
        horoscope = Horoscope.getById(id)
        initViews()

        setHoroscopeData(horoscope)
        fetchHoroscope(id)
    }

    private fun initViews() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.dateRange)

        iconView = findViewById(R.id.detailIconImageView)
        horoscopeName = findViewById(R.id.detailNameTextView)
        horoscopeDate = findViewById(R.id.detailDatesTextView)
        horoscopeDescription = findViewById(R.id.detailHoroscopeTextView)
        horoscopeProgressBar = findViewById(R.id.detailProgressBar)
    }

    private fun setHoroscopeData(horoscope: Horoscope) {
        iconView.setImageResource(horoscope.icon)
        horoscopeName.setText(horoscope.name)
        horoscopeDate.setText(horoscope.dateRange)
    }

    private fun fetchHoroscope(sign: String) {
        horoscopeProgressBar.visibility = View.VISIBLE
        horoscopeDescription.text = ""

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    HoroscopeApiService.create().getDailyHoroscope(sign)
                }


                horoscopeDescription.setTranslatedText(response.data.horoscope,
                        "en") //                horoscopeDescription.text = response.data.horoscope
                horoscopeProgressBar.visibility = View.GONE
            }
            catch (e: Exception) {
                horoscopeProgressBar.visibility = View.GONE
                horoscopeDescription.text = "Error loading horoscope: ${e.localizedMessage}"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)

        favoriteMenuItem = menu.findItem(R.id.favorite_menu)

        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.favorite_menu -> {
                if (isFavorite) {
                    session.setFavorite("")
                }
                else {
                    session.setFavorite(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                true
            }

            R.id.share_menu -> {
                val sendIntent = Intent().apply {
                    action = ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(sendIntent, R.string.share_horoscope.toString())
                startActivity(shareIntent)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setFavoriteIcon() {
        if (isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_full)
        }
        else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border)
        }
    }
}
