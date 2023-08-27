package com.bhive.beehiveapp

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.content.res.Resources.Theme
import android.graphics.Color
import android.os.Build

class ResourceAccess(original: Resources) :
    Resources(original.getAssets(), original.getDisplayMetrics(), original.getConfiguration()) {
    @Throws(NotFoundException::class)
    override fun getColor(id: Int): Int {
        return getColor(id, null)
    }

    @Throws(NotFoundException::class)
    override fun getColor(id: Int, theme: Theme?): Int {
        return when (getResourceEntryName(id)) {
            "your_special_color" ->                 // You can change the return value to an instance field that loads from SharedPreferences.
                Color.RED // used as an example. Change as needed.
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    super.getColor(id, theme)
                } else super.getColor(id)
            }
        }
    }
}