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
import com.bhive.beehiveapp.interfaces.NewsletterInterface
import com.bhive.beehiveapp.models.NewsletterModel
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold

class NewsletterAdapter : ListAdapter<NewsletterModel, NewsletterAdapter.ItemViewHolder>(DiffCallback()) {

    var newsletterInterface : NewsletterInterface?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.newslatter_row_layout2, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: NewsletterModel) = with(itemView) {

            val title =  itemView.findViewById<CustomTvSemiBold>(R.id.title)
            val forwardArrow =itemView.findViewById<ImageView>(R.id.forwardArrow)
            forwardArrow.setOnClickListener {
                newsletterInterface?.displayItem(position)
            }
            title.text = item.title
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<NewsletterModel>() {
        override fun areItemsTheSame(oldItem: NewsletterModel, newItem: NewsletterModel): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewsletterModel, newItem: NewsletterModel): Boolean {
            return oldItem == newItem
        }
    }
}
