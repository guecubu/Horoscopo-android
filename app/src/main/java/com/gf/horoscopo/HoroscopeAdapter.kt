package com.gf.horoscopo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter(val onItemClick: (Horoscope) -> Unit) :
        ListAdapter<Horoscope, HoroscopeViewHolder>(HoroscopeDiffCallback) {
    //  class HoroscopeAdapter(var items: List<Horoscope>, val onItemClick: (position: Int) -> Unit) :
    //            RecyclerView.Adapter<HoroscopeViewHolder>() {
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
//        val horoscope = items[position]
        val horoscope = getItem(position)
        holder.bind(horoscope)
        holder.itemView.setOnClickListener {
            onItemClick(horoscope)
        }
    }

    object HoroscopeDiffCallback : DiffUtil.ItemCallback<Horoscope>() {
        override fun areItemsTheSame(oldItem: Horoscope, newItem: Horoscope): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Horoscope, newItem: Horoscope): Boolean {
            return oldItem == newItem
        }
    }

//    override fun getItemCount(): Int {
//        return items.size
//    }

//    fun updateData(filteredList: List<Horoscope>) {
//        this.items = filteredList
//        notifyDataSetChanged()
//    }


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