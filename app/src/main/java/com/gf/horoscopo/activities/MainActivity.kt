package com.gf.horoscopo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gf.horoscopo.R
import com.gf.horoscopo.adapters.HoroscopeAdapter
import com.gf.horoscopo.data.Horoscope
import com.gf.horoscopo.utils.search

class MainActivity : androidx.appcompat.app.AppCompatActivity() {
    private val horoscopeList: List<Horoscope> = Horoscope.getAll()
    private lateinit var horoscopeRecyclerView: RecyclerView
    private lateinit var horoscopeAdapter: HoroscopeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        horoscopeRecyclerView = findViewById(
                R.id.horoscopeRecyclerView) //        horoscopeAdapter = HoroscopeAdapter(horoscopeList) { position ->
        horoscopeAdapter = HoroscopeAdapter { horoscope ->
            Log.d("MainActivity", "Horoscope clicked: $horoscope")
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("HOROSCOPE_ID", horoscope.id)
            }
            startActivity(intent)
        }
        horoscopeRecyclerView.adapter = horoscopeAdapter
        horoscopeRecyclerView.layoutManager = LinearLayoutManager(this)
        horoscopeAdapter.submitList(horoscopeList)

        supportActionBar?.setTitle(R.string.app_title)
        supportActionBar?.setSubtitle(R.string.app_subtitle)
    }

    override fun onResume() {
        super.onResume()

        horoscopeAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                val filteredList = horoscopeList.filter {
                    getString(it.name).search(newText) || getString(it.dateRange).search(newText)
                }

                horoscopeAdapter.submitList(
                        filteredList) //                horoscopeAdapter.updateData(filteredList)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> { //                startActivity(Intent(this, AboutActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
