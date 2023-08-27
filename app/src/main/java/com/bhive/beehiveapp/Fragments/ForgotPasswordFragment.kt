package com.bhive.beehiveapp.Fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentForgotPasswordBinding
import com.bhive.beehiveapp.interfaces.ForgotPasswordInterface
import com.bhive.beehiveapp.viewmodel.ForgotPasswordViewModel
import com.google.gson.JsonObject

class ForgotPasswordFragment : BaseFragment() , ForgotPasswordInterface{

    lateinit var forgotPasswordmodel: ForgotPasswordViewModel
    lateinit var jsonObject: JsonObject
    var ColorPrimary : String ? = null

    private lateinit var fragmentForgotPasswordBinding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentForgotPasswordBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forgot_password,
            container,
            false
        )
        return fragmentForgotPasswordBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forgotPasswordmodel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

        forgotPasswordmodel.forgotPasswordInterface=this
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        fragmentForgotPasswordBinding.submitBT.setOnClickListener {
            if (fragmentForgotPasswordBinding.emailET.text?.length ?: String == 0) {
                fragmentForgotPasswordBinding.emailET.error = "Email can't be empty"
                fragmentForgotPasswordBinding.emailET.requestFocus()
            }
            else if (!isEmailValid(fragmentForgotPasswordBinding.emailET.text.toString())) {
                fragmentForgotPasswordBinding.emailET.error = "Please enter valid email"
                fragmentForgotPasswordBinding.emailET.requestFocus()
            }
            else {
                jsonObject = JsonObject()
                jsonObject.addProperty("email",fragmentForgotPasswordBinding.emailET.text.toString())
                jsonObject.addProperty("role", 3)
                forgotPasswordmodel.forgot(jsonObject,requireContext(),fragmentForgotPasswordBinding)
            }
        }
        fragmentForgotPasswordBinding.backTV.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment2)
        }
        fragmentForgotPasswordBinding.backIV.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment2)
        }
    }

    private fun setColor() {
        fragmentForgotPasswordBinding.base.backgroundTintList = (ColorStateList.valueOf(Color.parseColor(ColorPrimary)))

    }

    override fun setMessage(msg: String) {
        Toast.makeText(requireActivity(),""+msg,Toast.LENGTH_LONG).show()
    }
}