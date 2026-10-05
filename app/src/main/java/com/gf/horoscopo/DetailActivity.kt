package com.gf.horoscopo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var iconView: ImageView
    private lateinit var horoscopeName: TextView
    private lateinit var horoscopeDescription: TextView
    private lateinit var horoscopeDate: TextView
    private lateinit var horoscopeProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        val horoscope = Horoscope.getById(id)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(horoscope.name)


        setHoroscopeData(horoscope)
        fetchHoroscope(id)
    }

    private fun initViews() {
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
                horoscopeProgressBar.visibility = View.GONE
                horoscopeDescription.text = response.data.horoscope
            } catch (e: Exception) {
                horoscopeProgressBar.visibility = View.GONE
                horoscopeDescription.text = "Error loading horoscope: ${e.localizedMessage}"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.favorite_menu -> {
//                shareHoroscope()
                true
            }

            R.id.share_menu -> {
//                shareHoroscope()
                true
            }

            else -> super.onOptionsItemSelected(item)

        }

    }
}
