package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.bhive.beehiveapp.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.models.BuildingFacilties
import com.bhive.beehiveapp.utils.fonts.CustomTvMedium
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.text.SimpleDateFormat
import java.util.*

class BuildingFacilitiesAdapter : ListAdapter<BuildingFacilties, BuildingFacilitiesAdapter.ItemViewHolder>(DiffCallback())  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.facilities_item_recycler, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var i = 1
        fun bind(item: BuildingFacilties) = with(itemView) {

            val facilityTitle = itemView.findViewById<CustomTvSemiBold>(R.id.facilityHeading)
            val facilityDescription = itemView.findViewById<CustomTvMedium>(R.id.facilityDescription)
            val openingTime = itemView.findViewById<CustomTvSemiBold>(R.id.openingTime)
            val closingTime = itemView.findViewById<CustomTvSemiBold>(R.id.closingTime)
            val facilitiesCount = itemView.findViewById<CustomTvSemiBold>(R.id.facilitiesCount)

            facilitiesCount.setBackgroundColor(android.graphics.Color.parseColor(SharedPreferencesClass.getString(context , "primaryColor").toString()))
            facilityTitle.setText(item.fName)
            facilityDescription.setText(item.fLocation)
            val opTime = convertTo12Hours(item.fOpening)
            val closeTime = convertTo12Hours(item.fClosing)
            openingTime.setText(opTime)
            closingTime.setText(closeTime)
            facilitiesCount.setText((position+1).toString())

        }
    }

    fun convertTo12Hours(militaryTime: String): String{
        val inputFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val date = inputFormat.parse(militaryTime)
        return outputFormat.format(date)
    }

    class DiffCallback : DiffUtil.ItemCallback<BuildingFacilties>() {
        override fun areItemsTheSame(oldItem: BuildingFacilties, newItem: BuildingFacilties): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: BuildingFacilties, newItem: BuildingFacilties): Boolean {
            return oldItem == newItem
        }
    }



}
