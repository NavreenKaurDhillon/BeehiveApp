package com.bhive.beehiveapp.Fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bhive.beehiveapp.databinding.FragmentAfterhourserviceBinding
import java.io.File

class AfterHourServiceFragment : com.bhive.beehiveapp.Fragments.BaseFragment() {
    private lateinit var afterhourserviceBinding: FragmentAfterhourserviceBinding
    var ColorPrimary : String ? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        afterhourserviceBinding = DataBindingUtil.inflate(
            inflater,
            com.bhive.beehiveapp.R.layout.fragment_afterhourservice,
            container,
            false
        )
        return afterhourserviceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val bitmapFile =
//            File( "http://staging.qualhon.net:3088/uploads/issues/images-1663913514349.jpg]")
//        val bitmap = BitmapFactory.decodeFile(bitmapFile.toString())
//        afterhourserviceBinding.iv.setImageBitmap(bitmap)

        if (com.bhive.beehiveapp.Fragments.SharedPreferencesClass.Companion.getString(
                requireContext(),
                "primaryColor"
            ) != null)
        {
            ColorPrimary = com.bhive.beehiveapp.Fragments.SharedPreferencesClass.Companion.getString(
                requireContext(),
                "primaryColor"
            ).toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        afterhourserviceBinding.callLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:"+"123 456 789 321")
            startActivity(intent)
        }
        afterhourserviceBinding.emailLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val sendto : String ="after.hours@gmail.com"
            val addressees = arrayOf(sendto)
            intent.putExtra(Intent.EXTRA_EMAIL, addressees)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }
    }

    private fun setColor() {
        afterhourserviceBinding.baseLayoutAfter.setBackgroundColor(Color.parseColor(ColorPrimary))
        afterhourserviceBinding.callIcon.imageTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))
        afterhourserviceBinding.mailIcon.imageTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }


}