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
import com.bhive.beehiveapp.models.BuildingFacilties
import com.bhive.beehiveapp.models.BuildingRules
import com.bhive.beehiveapp.utils.fonts.CustomTvMedium
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.text.SimpleDateFormat
import java.util.*

class BuildingRulesAdapter : ListAdapter<BuildingRules, BuildingRulesAdapter.ItemViewHolder>(DiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rules_recycler_item, parent, false)
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
        fun bind(item: BuildingRules) = with(itemView) {
            val ruleTitle = itemView.findViewById<CustomTvSemiBold>(R.id.facilityHeading)
            val ruleDescription = itemView.findViewById<CustomTvMedium>(R.id.facilityDescription)
            val ruleCount = itemView.findViewById<CustomTvSemiBold>(R.id.facilitiesCount)

            ruleCount.setBackgroundColor(
                Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))

            ruleTitle.setText(item.title)
            ruleDescription.setText(item.description)
            ruleCount.setText((position+1).toString())

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BuildingRules>() {
        override fun areItemsTheSame(oldItem: BuildingRules, newItem: BuildingRules): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: BuildingRules, newItem: BuildingRules): Boolean {
            return oldItem == newItem
        }
    }



}
