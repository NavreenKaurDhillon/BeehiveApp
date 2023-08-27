package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentChangePasswordBinding
import com.bhive.beehiveapp.viewmodel.ChangePasswordViewModel
import com.google.gson.JsonObject

class ChangePasswordFragment : BaseFragment() {

    lateinit var jsonObject: JsonObject
    lateinit var changePasswordViewModel: ChangePasswordViewModel
    private lateinit var changePasswordBinding: FragmentChangePasswordBinding
    var ColorPrimary : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changePasswordBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_change_password,container,false)
        return changePasswordBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePasswordViewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java )
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        changePasswordBinding.backIcon.setOnClickListener{
            findNavController().navigate(R.id.settingsFragment)
        }
        changePasswordBinding.submitBT.setOnClickListener {

            if (changePasswordBinding.currentET.text?.length ?: String==0) {
                changePasswordBinding.currentET.error = "Please enter current password"
                changePasswordBinding.currentET.requestFocus()
            }
            else if (changePasswordBinding.newET.text?.length ?: String==0) {
                changePasswordBinding.newET.error = "Please enter new password"
                changePasswordBinding.newET.requestFocus()
            }
            else if (!isValidPassword(changePasswordBinding.newET.text.toString()) || changePasswordBinding.newET.text.toString() < 8.toString()) {
                changePasswordBinding.newET.error = "Password must contain at least 6 characters including Upper/Lower case, 1 numeric digit and 1 special character"
                changePasswordBinding.newET.requestFocus()
            }
            else if (changePasswordBinding.confirmET.text?.length ?: String==0) {
                changePasswordBinding.confirmET.error = "Confirm password can't be empty"
                changePasswordBinding.confirmET.requestFocus()
            }
            else if (changePasswordBinding.newET.text.toString()?.equals(changePasswordBinding.confirmET.text.toString()) == false)
            {
                changePasswordBinding.confirmET.error = "New and confirm password doesn't match"
                changePasswordBinding.confirmET.requestFocus()
            }
            else if ( changePasswordBinding.currentET.text.toString()?.equals(changePasswordBinding.newET.text.toString()) == true) {
                changePasswordBinding.newET.error = "Current and new password can't be same"
                changePasswordBinding.newET.requestFocus()
            }
            else {
                jsonObject = JsonObject()
                jsonObject.addProperty("old_password", changePasswordBinding.currentET.text.toString() )
                jsonObject.addProperty("new_password", changePasswordBinding.newET.text.toString() )
                changePasswordViewModel.change(jsonObject, requireContext(),view)
            }
        }

    }

    private fun setColor() {
        changePasswordBinding.submitBT.backgroundTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }
}