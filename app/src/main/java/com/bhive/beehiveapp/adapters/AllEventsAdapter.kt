package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.AllEvents
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvMedium
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.lang.System.currentTimeMillis
import java.security.Timestamp
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class AllEventsAdapter : ListAdapter<AllEvents, AllEventsAdapter.ItemViewHolder>(DiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.calender_row_layout, parent, false)
        )}

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position) )
    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: AllEvents) = with(itemView) {
            val title = itemView.findViewById<CustomTvSemiBold>(R.id.title)
            val description = itemView.findViewById<CustomTvSemiBold>(R.id.description)
            val eventDate = itemView.findViewById<CustomTvMedium>(R.id.eventDate)
            title.text = item.title
            title.setText(item.title)
            description.setText(item.description)
            val pattern = "hh:mm a"

//            val ts : Timestamp = Timestamp(item.eventDate.toLong())
            val instant: Instant = Instant.ofEpochMilli(item.eventDate!!)
            val nf: NumberFormat = NumberFormat.getInstance()
            nf.setMinimumIntegerDigits(2)
            val fin: LocalDateTime = LocalDateTime.ofInstant(instant , ZoneId.of("Asia/Kolkata"))
            eventDate.setText(nf.format(fin.dayOfMonth).toString()+"-"+nf.format(fin.monthValue).toString()+"-"+fin.year+"  "+
                    fin.format(DateTimeFormatter.ofPattern(pattern)))
            val sideLine = itemView.findViewById<View>(R.id.viewSide)

            if (Constant.PRIMARY_COLOR!=null){
                Log.d(TAG, "bind: // const colr = "+Constant.PRIMARY_COLOR)
                sideLine.setBackgroundColor(Color.parseColor(Constant.PRIMARY_COLOR))
                eventDate.setTextColor(Color.parseColor(Constant.PRIMARY_COLOR))
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AllEvents>() {
        override fun areItemsTheSame(oldItem: AllEvents, newItem: AllEvents): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AllEvents, newItem: AllEvents): Boolean {
            return oldItem == newItem
        }
    }
}


