package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.NotificationsFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.interfaces.NotificationsInterface
import com.bhive.beehiveapp.models.Notifications
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvLight
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold

class NotificationsAdapter : ListAdapter<Notifications, NotificationsAdapter.ItemViewHolder>(DiffCallback())  {

     var notificationsInterface : NotificationsInterface?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Notifications) = with(itemView) {
            val title =itemView.findViewById<CustomTvSemiBold>(R.id.titleTV)
            val description =itemView.findViewById<CustomTvLight>(R.id.descriptionTV)
            val deleteIcon =itemView.findViewById<ImageView>(R.id.deleteIcon)
            val icon = itemView.findViewById<ImageView>(R.id.iIcon)
            val line = itemView.findViewById<View>(R.id.line)
            val baseLayout = itemView.findViewById<CardView>(R.id.baseNotificationLayout)
            icon.imageTintList = (ColorStateList.valueOf(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor"))))
            line.setBackgroundColor(Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            title.setText(item.title)
            description.setText(item.des)
            baseLayout.setOnClickListener {
                 if (item.type ==1)
                 {
                     Constant.issueId = item.id.toInt()
                     findNavController().navigate(R.id.action_notificationsFragment_to_reportAnIssueFragment)
                 }

            }
            deleteIcon.setOnClickListener(View.OnClickListener{
                if(notificationsInterface==null){
                    notificationsInterface = NotificationsFragment()

                }
//                Constant.notificationsFragment1.showDeleteNotificationDialog(position , item.id, context)

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Are you sure?")
                builder.setMessage("Do you really want to delete this notification? This process cannot be undone.")
                    .setPositiveButton("Cancel", DialogInterface.OnClickListener {
                            dialog, id -> dialog.dismiss()
                    })
                    // negative button text and action
                    .setNegativeButton("Delete", DialogInterface.OnClickListener {
                            dialog, id -> notificationsInterface?.deleteNotification(position, item.id)
                    })

                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()

                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()


                })
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<Notifications>() {
        override fun areItemsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem == newItem
        }
    }
}
