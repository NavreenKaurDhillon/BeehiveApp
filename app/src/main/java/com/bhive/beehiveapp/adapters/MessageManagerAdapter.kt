package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.BaseFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.Messages
import com.bhive.beehiveapp.models.MessagesToManager
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.util.*

class MessageManagerAdapter : ListAdapter<MessagesToManager, MessageManagerAdapter.ItemViewHolder>(DiffCallback())
{
    var senderInitials : String ? = null
    val baseFragment = BaseFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reply_manager_recycler_layout, parent, false)
        )}
    
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        holder.bind(getItem(position) )
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: MessagesToManager) = with(itemView) {
            val message = itemView.findViewById<CustomTvRegular>(R.id.messageBubble)
            val image = itemView.findViewById<CustomTvSemiBold>(R.id.senderImage)
            image.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            val time = itemView.findViewById<CustomTvRegular>(R.id.time)
            val messageLayout = itemView.findViewById<LinearLayout>(R.id.messageLayout)
            messageLayout.setBackgroundColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))

//            name.setTextColor(
//                Color.parseColor(Constant.PRIMARY_COLOR))
            Log.d(ContentValues.TAG, "bind: // message from other = "+item.message)
            Log.d(ContentValues.TAG, "bind: // message from other name = "+item.userName)
            message.text = item.message.toString()
            image.text = item.userName?.get(0)?.toString()
            time.setText("Just now")
//            if (senderInitials == item.userName?.get(0)?.toString())
//            {
//                image.visibility = View.GONE
//                name.visibility = View.GONE
//            }
//            else
//            {
//                image.visibility = View.VISIBLE
//                name.visibility = View.VISIBLE
//            }
            senderInitials = item.userName?.get(0)?.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MessagesToManager>() {
        override fun areItemsTheSame(oldItem: MessagesToManager, newItem: MessagesToManager): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MessagesToManager, newItem: MessagesToManager): Boolean {
            return oldItem == newItem
        }
    }
}