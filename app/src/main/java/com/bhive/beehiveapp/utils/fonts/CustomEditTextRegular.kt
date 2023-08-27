package com.bhive.beehiveapp.utils.fonts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import com.bhive.beehiveapp.R

@SuppressLint("AppCompatCustomView")

class CustomEditTextRegular @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        init()
    }


    private  fun init() {
            val font = Typeface.createFromAsset(context.assets, "muli_light.ttf")
        this.typeface = font
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        var tf = tf
        tf = Typeface.createFromAsset(context.assets, "muli_light.ttf")
        super.setTypeface(tf, style)
    }

    override fun setTypeface(tf: Typeface?) {
        var tf = tf
        tf = Typeface.createFromAsset(context.assets, "muli_light.ttf")
        super.setTypeface(tf)
    }
}
