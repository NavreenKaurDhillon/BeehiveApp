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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.BaseFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.GetIssueData
import com.bhive.beehiveapp.models.Messages
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.util.*

class UserMessageAdapter : ListAdapter<Messages, UserMessageAdapter.ItemViewHolder>(DiffCallback())
{
    var senderInitials : String ? = null
    var senderInitialsL : String ? = null
    var baseFragment = BaseFragment()
    var oince = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_messages, parent, false)
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
            val messageL= itemView.findViewById<CustomTvRegular>(R.id.messageBubbleL)
            val left = itemView.findViewById<RelativeLayout>(R.id.left)
            val right = itemView.findViewById<RelativeLayout>(R.id.right)
            val image = itemView.findViewById<CustomTvSemiBold>(R.id.senderImage)
            val imageL = itemView.findViewById<CustomTvSemiBold>(R.id.senderImageL)
            image.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            imageL.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            val time = itemView.findViewById<CustomTvRegular>(R.id.time)
            val timeL = itemView.findViewById<CustomTvRegular>(R.id.timeL)
            val name = itemView.findViewById<CustomTvRegular>(R.id.userName)
            val nameL = itemView.findViewById<CustomTvRegular>(R.id.userNameL)
            name.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            nameL.setTextColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            val messageLayout = itemView.findViewById<LinearLayout>(R.id.messageLayout)
                val messageLayoutL = itemView.findViewById<RelativeLayout>(R.id.messageLayoutL)
            messageLayout.setBackgroundColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
//            messageLayoutL.setBackgroundColor(Color.parseColor(R.color.Beige.toString()))
            val userId  = SharedPreferencesClass.getString(context,"userID")?.toInt()

            Log.d(TAG, "bind: /// user id = "+userId+"  sender id  = "+item.sendBy)
            if (userId == item.sendBy)
            {
                Log.d(TAG, "bind: // message from user = "+item.message)

                image.setText(item.sendByName[0].toString())
                message.setText(item.message.toString())
                Log.d(TAG, "bind: //// created at = "+item.createdAt)
                val df = (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH))
                df.setTimeZone(TimeZone.getTimeZone("UTC"))
                val date: Date = df.parse(item.createdAt)
                df.setTimeZone(TimeZone.getDefault())
                val formattedDate: String = df.format(date)
                Log.d(TAG, "bind: /// formatted date = "+formattedDate)

                var timeStamp : Long =   (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(formattedDate).getTime())

                Log.d(TAG, "bind: /// timestamp calculated = // "+ timeStamp)
                var msg : String = baseFragment.timeAgo(timeStamp).toString()
                time.setText(msg)
                name.setText(item.sendByName)
                if (senderInitials == item.sendByName[0].toString())
                {
                    name.visibility = View.GONE

                }
                else{
                    image.visibility = View.VISIBLE
                    name.visibility = View.VISIBLE
                }

                senderInitials = item.sendByName[0].toString()
                left.visibility = View.GONE
            }
            else{
                nameL.setText(item.sendByName.toString())
                imageL.setText(item.sendByName[0].toString())
                Log.d(TAG, "bind: /// manager message = "+item.message)
                messageL.setText(item.message.toString())
                Log.d(TAG, "bind: //// created at = "+item.createdAt)
                val df = (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH))
                df.setTimeZone(TimeZone.getTimeZone("UTC"))
                val date: Date = df.parse(item.createdAt)
                df.setTimeZone(TimeZone.getDefault())
                val formattedDate: String = df.format(date)
                Log.d(TAG, "bind: /// formatted date = "+formattedDate)

                var timeStamp : Long =   (java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(formattedDate).getTime())

                Log.d(TAG, "bind: /// timestamp calculated = // "+ timeStamp)
                var msg : String = baseFragment.timeAgo(timeStamp).toString()
                timeL.setText(msg)
                nameL.setText(item.sendByName)
                if (senderInitialsL == item.sendByName[0].toString())
                {
                    nameL.visibility = View.GONE

                }
                else{
                    image.visibility = View.VISIBLE
                    nameL.visibility = View.VISIBLE
                }

                senderInitialsL = item.sendByName[0].toString()
                right.visibility = View.GONE
            }
            }


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
