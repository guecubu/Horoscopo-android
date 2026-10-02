package com.gf.horoscopo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {

    lateinit var iconView: ImageView
    lateinit var horoscopeName: TextView
    lateinit var horoscopeDescription: TextView
    lateinit var horoscopeDate: TextView


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
        setHoroscopeData(horoscope)

    }

    fun initViews() {
        iconView = findViewById(R.id.iconDetailImageView)
        horoscopeName = findViewById(R.id.horoscopeNameDetailTextView)
        horoscopeDate = findViewById(R.id.horoscopeDatesDetailTextView)
    }

    fun setHoroscopeData(horoscope: Horoscope) {
        iconView.setImageResource(horoscope.icon)
        horoscopeName.setText(horoscope.name)
        horoscopeDate.setText(horoscope.dateRange)
    }
}