package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.CalendarEvents
import com.bhive.beehiveapp.utils.fonts.CustomTvMedium
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold

class CalendarEventsAdapter : ListAdapter<CalendarEvents, CalendarEventsAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
    {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.calender_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(item: CalendarEvents) = with(itemView) {
            val eventTitle =  itemView.findViewById<CustomTvSemiBold>(R.id.title)
            val eventDescription=itemView.findViewById<CustomTvSemiBold>(R.id.description)
            val line = itemView.findViewById<View>(R.id.viewSide)
            val eventDate = itemView.findViewById<CustomTvMedium>(R.id.eventDate)
            eventDate.visibility = View.GONE
            line.setBackgroundColor(
                Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            eventTitle.setText(item.agenda)
            eventDescription.setText(item.description)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CalendarEvents>() {
        override fun areItemsTheSame(oldItem: CalendarEvents, newItem: CalendarEvents): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CalendarEvents, newItem: CalendarEvents): Boolean {
            return oldItem == newItem
        }
    }
}
