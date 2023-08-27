package com.bhive.beehiveapp.adapters

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.interfaces.ReportAnIssueInterface
import com.bhive.beehiveapp.models.AllEvents
import com.bhive.beehiveapp.models.IssuesModelClass
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class BuildingIssuesAdapter : ListAdapter<IssuesModelClass, BuildingIssuesAdapter.ItemViewHolder>(DiffCallback())
{
    var reportAnIssueInterface: ReportAnIssueInterface?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.report_issue_main_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        Log.d(TAG, "onBindViewHolder: /////"+ holder+"pos //"+position)
        holder.bind(getItem(position) )
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: IssuesModelClass) = with(itemView) {
            val baseLayout = itemView.findViewById<RelativeLayout>(R.id.baseLayout)
            val userName =  itemView.findViewById<CustomTvSemiBold>(R.id.userName)
            val description=itemView.findViewById<CustomTvRegular>(R.id.issueDescription)
            val title=itemView.findViewById<CustomTvSemiBold>(R.id.issueType)
            val date = itemView.findViewById<CustomTvSemiBold>(R.id.issueDate)
            val complainant = itemView.findViewById<CustomTvSemiBold>(R.id.userName)
            val status = itemView.findViewById<CustomTvRegular>(R.id.status)
            status.setTextColor(
                Color.parseColor(SharedPreferencesClass.getString(context,"primaryColor")))
            title.setText(item.title)
            description.setText(item.description)
            complainant.setText(item.complainant)

            baseLayout.setOnClickListener(View.OnClickListener{
                Log.d(TAG, "bind: /// base layout clickrf")
//
                Constant.issueId = item.id
                Log.d(TAG, "bind: ///id id ///"+Constant
                    .issueId)
                findNavController().navigate(R.id.reportAnIssueFragment)

            })
//            val format = SimpleDateFormat(
//                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
//            )
//           format.timeZone = TimeZone.getTimeZone("UTC")
            val pattern = "hh:mm a"
            val instant: Instant = Instant.parse(item.createdAt)
            val nf: NumberFormat = NumberFormat.getInstance()
            nf.setMinimumIntegerDigits(2)
            val fin: LocalDateTime = LocalDateTime.ofInstant(instant , ZoneId.of("Asia/Kolkata"))
            date.setText(nf.format(fin.dayOfMonth).toString()+"-"+nf.format(fin.monthValue).toString()+"-"+fin.year+"  "+
                    fin.format(DateTimeFormatter.ofPattern(pattern)))

            if(item.status == 1)
            {
                status.setText("Open")
            }
            else if(item.status == 2)
            {
                status.setText("Closed")
            }
            else if(item.status == 3)
            {
                status.setText("Resolved")
            }
            else
            {
                status.setText("Closed")
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<IssuesModelClass>() {
        override fun areItemsTheSame(oldItem: IssuesModelClass, newItem: IssuesModelClass): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: IssuesModelClass, newItem: IssuesModelClass): Boolean {
            return oldItem == newItem
        }
    }
}


