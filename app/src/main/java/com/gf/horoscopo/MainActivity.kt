package com.gf.horoscopo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val horoscopeList: List<Horoscope> = Horoscope.getAll()
    lateinit var horoscopeRecyclerView: RecyclerView
    lateinit var horoscopeAdapter: HoroscopeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        horoscopeRecyclerView = findViewById(R.id.horoscopeRecyclerView)
        horoscopeAdapter = HoroscopeAdapter(horoscopeList) { position ->
            val horoscope = horoscopeList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("HOROSCOPE_ID", horoscope.id)
            startActivity(intent)
        }
        horoscopeRecyclerView.adapter = horoscopeAdapter
        horoscopeRecyclerView.layoutManager = LinearLayoutManager(this)
    }


}