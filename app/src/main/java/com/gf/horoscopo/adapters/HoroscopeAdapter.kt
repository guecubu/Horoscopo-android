package com.gf.horoscopo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gf.horoscopo.R
import com.gf.horoscopo.data.Horoscope
import com.gf.horoscopo.utils.SessionManager

class HoroscopeAdapter(val onItemClick: (Horoscope) -> Unit) :
        androidx.recyclerview.widget.ListAdapter<Horoscope, HoroscopeViewHolder>(
                HoroscopeDiffCallback) {
    //  class HoroscopeAdapter(var items: List<Horoscope>, val onItemClick: (position: Int) -> Unit) :
    //            RecyclerView.Adapter<HoroscopeViewHolder>() {
    var isGridView: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {

        val layout = if (viewType == 1) R.layout.item_horoscope_grid
        else R.layout.item_horoscope_list

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return HoroscopeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = getItem(position)
        holder.bind(horoscope)
        holder.itemView.setOnClickListener {
            onItemClick(horoscope)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridView) 1 else 0
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

    val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)

    fun bind(horoscope: Horoscope) {

        iconImageView.setImageResource(horoscope.icon)
        nameTextView.setText(horoscope.name)
        dateRangeTextView.setText(horoscope.dateRange)
        favoriteImageView.isVisible = SessionManager(itemView.context).isFavorite(horoscope.id)

        //        if (SessionManager(itemView.context).isFavorite(horoscope.id)) {
        //            favoriteImageView.visibility = View.VISIBLE
        //        }
        //        else {
        //            favoriteImageView.visibility = View.GONE
        //        }

    }

}