package com.bhive.beehiveapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.models.SpinnerItemsModelClass


class DropDownSpinnerAdapter (ctx: Context, countries: ArrayList<SpinnerItemsModelClass>) : ArrayAdapter<SpinnerItemsModelClass>(ctx, 0, countries) {

    override fun getCount(): Int {
        return super.getCount()

    }
    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    fun createItemView(position: Int, recycledView: View?, parent: ViewGroup):View {
        val country = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(R.layout.spinner_layout,
            parent,
            false
        )

        country?.let {
            val icon=  view.findViewById<ImageView>(R.id.icon)
            val text=  view.findViewById<TextView>(R.id.text)
            val base = view.findViewById<RelativeLayout>(R.id.relativeLayout)

            icon.setImageResource(country.icon!!)
            text.text = country.society_name
//            if(position == 0){
//                text.setTextColor(context.getColor(R.color.DarkGray))
//            }

        }
        return view
    }
}

