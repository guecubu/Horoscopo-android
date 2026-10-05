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
}