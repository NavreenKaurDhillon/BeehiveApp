package com.bhive.beehiveapp.utils.fonts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton


@SuppressLint("AppCompatCustomView")
class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    init {
       init()
    }

    private fun init() {
        val font = Typeface.createFromAsset(context.assets, "poppins_medium.ttf")
        this.typeface = font
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        var tf = tf
        tf = Typeface.createFromAsset(context.assets, "poppins_medium.ttf")
        super.setTypeface(tf, style)
    }

    override fun setTypeface(tf: Typeface?) {
        var tf = tf
        tf = Typeface.createFromAsset(context.assets, "poppins_medium.ttf")
        super.setTypeface(tf)
    }
}
