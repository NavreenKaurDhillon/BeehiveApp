package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues
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
import com.bhive.beehiveapp.Fragments.BaseFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.Messages
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.util.*

class LeftMessageAdapter : ListAdapter<Messages, LeftMessageAdapter.ItemViewHolder>(DiffCallback())
    {
        var senderInitials : String ? = null
        val baseFragment = BaseFragment()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.manager_messages, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
        {
            holder.bind(getItem(position) )
        }

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            @RequiresApi(Build.VERSION_CODES.O)
            fun bind(item: Messages) = with(itemView) {
                val message = itemView.findViewById<CustomTvRegular>(R.id.messageBubble)
                val image = itemView.findViewById<CustomTvSemiBold>(R.id.senderImage)
                image.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
                val time = itemView.findViewById<CustomTvRegular>(R.id.time)
                val name = itemView.findViewById<CustomTvRegular>(R.id.senderName)
                name.setTextColor(
                    Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))

                message.setText(item.message)
                name.setText(item.sendByName)
//                time.setText(DateUtils.getRelativeTimeSpanString(context , 1668507306 ))
                image.setText(item.sendByName[0].toString())
                Log.d(TAG, "bind: /// created at = "+item.createdAt)

                val df = (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH))
                df.setTimeZone(TimeZone.getTimeZone("UTC"))
                val date: Date = df.parse(item.createdAt)
                df.setTimeZone(TimeZone.getDefault())
                val formattedDate: String = df.format(date)
                Log.d(TAG, "bind: /// formatted date = "+formattedDate)


                var istDate = (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(formattedDate))
//                var timestamp2 : Long =
                var timeStamp : Long =   (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(item.createdAt).getTime())

                Log.d(TAG, "bind: /// timestamp calcul;ated = // "+ timeStamp)
                var msg : String = baseFragment.timeAgo(timeStamp).toString()
                time.setText(msg)
                if (senderInitials == item.sendByName[0].toString())
                {
                    image.visibility = View.GONE
                    name.visibility = View.GONE

                }
                else{
                    image.visibility = View.VISIBLE
                    name.visibility = View.VISIBLE
                }

                senderInitials = item.sendByName[0].toString()

            }
        }

        class DiffCallback : DiffUtil.ItemCallback<Messages>() {
            override fun areItemsTheSame(oldItem: Messages, newItem: Messages): Boolean {
                return oldItem.toString() == newItem.toString()
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Messages, newItem: Messages): Boolean {
                return oldItem == newItem
            }
        }
    }