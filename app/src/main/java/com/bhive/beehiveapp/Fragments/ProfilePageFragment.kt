package com.bhive.beehiveapp.Fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentProfilePageBinding
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.GetProfileViewModel
import com.bhive.beehiveapp.viewmodel.UpdateProfileViewModel
import com.google.gson.JsonObject


class ProfilePageFragment : BaseFragment() {

    lateinit var jsonObject : JsonObject
     open lateinit var fragmentProfilePageBinding: FragmentProfilePageBinding
    lateinit var getProfileViewModel: GetProfileViewModel
    lateinit var updateProfileViewModel: UpdateProfileViewModel
    val REQUEST_CODE = 100
     var dataUri : Uri?= null
    var ColorPrimary : String ? = null

    companion object
    {
        var FIRST_NAME : String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfilePageBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page,container,false)
        return fragmentProfilePageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfileViewModel = ViewModelProvider(this).get(GetProfileViewModel::class.java)
        getProfileViewModel.userProfile()
        updateProfileViewModel = ViewModelProvider(this).get(UpdateProfileViewModel::class.java)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        fragmentProfilePageBinding.backIcon.setOnClickListener {
            findNavController().navigate(R.id.action_profilePageFragment_to_settingsFragment)
        }
        fragmentProfilePageBinding.closeIcon.setOnClickListener {
            findNavController().navigate(R.id.action_profilePageFragment_to_settingsFragment)
        }
        getProfileViewModel.setUserProfile(fragmentProfilePageBinding)
//        setProfileData()


//        fragmentProfilePageBinding.imageIcon.setOnClickListener {
//            openGalleryForImage()
//        }
//
//        fragmentProfilePageBinding.backIV.setOnClickListener {
//            findNavController().navigate(R.id.settingsFragment)
//
//        }
        fragmentProfilePageBinding.updateBT.setOnClickListener {
            if (fragmentProfilePageBinding.firstNameET.text?.length ?: String == 0){
                fragmentProfilePageBinding.firstNameET.error = "First name can't be empty"
                fragmentProfilePageBinding.firstNameET.requestFocus()
            }
            else if (fragmentProfilePageBinding.lastNameET.text?.length ?: String == 0) {
                fragmentProfilePageBinding.lastNameET.error = "Last name can't be empty"
                fragmentProfilePageBinding.lastNameET.requestFocus()
            }
            else if (fragmentProfilePageBinding.emailET.text?.length ?: String==0) {
                fragmentProfilePageBinding.emailET.error = "Email can't be empty"
                fragmentProfilePageBinding.emailET.requestFocus()
            }
            else if (fragmentProfilePageBinding.phoneNumberET.text?.length ?: String==0) {
                fragmentProfilePageBinding.phoneNumberET.error = "Phone number can't be empty"
                fragmentProfilePageBinding.phoneNumberET.requestFocus()
            }
            else if (fragmentProfilePageBinding.phoneNumberET.text.toString().length!=10 )
            {
                fragmentProfilePageBinding.phoneNumberET.error = "Please enter valid phone number"
                fragmentProfilePageBinding.phoneNumberET.requestFocus()
            }
            else if (!isEmailValid(fragmentProfilePageBinding.emailET.text.toString())) {
                fragmentProfilePageBinding.emailET.error = "Please enter valid email"
                fragmentProfilePageBinding.emailET.requestFocus()
            }
            else
            {
                jsonObject = JsonObject()
            jsonObject.addProperty("first_name", fragmentProfilePageBinding.firstNameET.text.toString())
            jsonObject.addProperty("last_name", fragmentProfilePageBinding.lastNameET.text.toString())
            jsonObject.addProperty("phone", fragmentProfilePageBinding.phoneNumberET.text.toString())
            jsonObject.addProperty("email", fragmentProfilePageBinding.emailET.text.toString())
            updateProfileViewModel.updateUserProfile(jsonObject, requireContext(),view)
        }
        }
    }

    private fun setColor() {
        fragmentProfilePageBinding.updateBT.backgroundTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }

    private fun openGalleryForImage()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
//            fragmentProfilePageBinding.text.visibility = View.GONE
            dataUri = data?.data
//            fragmentProfilePageBinding.profileIV.setImageURI(data?.data) // handle chosen image
        }
    }

    fun setProfileData() {
        Log.d(TAG, "setProfileData: /// first name = "+Constant.FIRST_NAME)
        Log.d(TAG, "setProfileData: /// first name v= "+FIRST_NAME)
        fragmentProfilePageBinding.firstNameET.setText(Constant.FIRST_NAME.toString())
        fragmentProfilePageBinding.lastNameET.setText(Constant.LAST_NAME)
        fragmentProfilePageBinding.emailET.setText(Constant.EMAIL)
        fragmentProfilePageBinding.phoneNumberET.setText(Constant.PHONE)
    }
}


