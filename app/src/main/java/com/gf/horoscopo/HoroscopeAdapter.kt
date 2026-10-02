package com.gf.horoscopo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter(val items: List<Horoscope>, val onItemClick: (position: Int) -> Unit) :
        RecyclerView.Adapter<HoroscopeViewHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): HoroscopeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        return HoroscopeViewHolder(view)
    }

    override fun onBindViewHolder(
            holder: HoroscopeViewHolder,
            position: Int
    ) {
        val horoscope = items[position]
        holder.bind(horoscope)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    val nameTextView: TextView = view.findViewById(R.id.horoscopeNameTextView)
    val dateRangeTextView: TextView = view.findViewById(R.id.horoscopeDatesTextView)

    fun bind(horoscope: Horoscope) {
        iconImageView.setImageResource(horoscope.icon)
        nameTextView.setText(horoscope.name)
        dateRangeTextView.setText(horoscope.dateRange)

    }

}