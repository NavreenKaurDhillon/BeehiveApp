package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.interfaces.DrawerItemsInterface
import com.bhive.beehiveapp.models.DrawerItemsModelClass
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold

class DrawerItemsAdapter : ListAdapter<DrawerItemsModelClass, DrawerItemsAdapter.ItemViewHolder>(DiffCallback()) {

    var drawerItemsInterface: DrawerItemsInterface? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.drawer_recycler_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var last_position: View? = null
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: clicked here" + position)
            var itemm = getItem(position)
//            holder.itemView.findViewById<CustomTvRegular>(R.id.title)
//                .setBackgroundColor("#F0AB08".toInt())

//            holder.itemView.findViewById<RelativeLayout>(R.id.RL).setBackgroundColor("#ffffff".toInt())

            var selectedItemPosition = position

            holder.itemView.setBackgroundResource(R.drawable.spinner_shape)
            notifyDataSetChanged()
        }
    }



    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        fun bind(item: DrawerItemsModelClass) = with(itemView) {

           var item1 : RelativeLayout ?= null
            val icon =  itemView.findViewById<ImageView>(R.id.icon)
            val RL = itemView.findViewById<RelativeLayout>(R.id.RL)
            val title=itemView.findViewById<CustomTvSemiBold>(R.id.title)
            title.text = item.title
            icon.setImageResource(item.icon)
            val item = itemView.findViewById<CustomTvSemiBold>(R.id.title)

            item1?.setBackgroundColor(getResources().getColor(R.color.colorPrimary))


            item.setOnClickListener{

//                RL.setBackgroundColor(getResources().getColor(R.color.black))
                drawerItemsInterface?.openFragment(position,it)
                item1 = RL


            }
            RL.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            RL.setBackgroundDrawable(null)
        }

        override fun onClick(v: View?) {
//            v?.findViewById<RelativeLayout>(R.id.RL)?.setBackgroundDrawable(selected_tab.toDrawable())
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<DrawerItemsModelClass>() {
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DrawerItemsModelClass, newItem: DrawerItemsModelClass): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: DrawerItemsModelClass,
            newItem: DrawerItemsModelClass
        ): Boolean {
            return oldItem.toString() == newItem.toString()

        }
    }
}

