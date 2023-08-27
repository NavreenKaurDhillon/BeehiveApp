package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.MarketPlaceModel
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold

class MarketPlaceAdapter :  ListAdapter<MarketPlaceModel, MarketPlaceAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.marketplace_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MarketPlaceModel) = with(itemView) {
            val title =  itemView.findViewById<CustomTvSemiBold>(R.id.titleTV)
            val description=itemView.findViewById<CustomTvSemiBold>(R.id.descriptionTV)
            val imageView=itemView.findViewById<ImageView>(R.id.imageView)
            imageView.setImageDrawable(item.img)
            title.text = item.title
            description.text = item.description
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<MarketPlaceModel>() {
        override fun areItemsTheSame(oldItem: MarketPlaceModel, newItem: MarketPlaceModel): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MarketPlaceModel, newItem: MarketPlaceModel): Boolean {
            return oldItem == newItem
        }
    }
}
