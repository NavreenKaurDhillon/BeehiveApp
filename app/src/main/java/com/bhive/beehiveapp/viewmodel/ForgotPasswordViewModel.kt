package com.bhive.beehiveapp.viewmodel

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentForgotPasswordBinding
import com.bhive.beehiveapp.interfaces.ForgotPasswordInterface
import com.bhive.beehiveapp.models.DataModal
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordViewModel : ViewModel() {


    var forgotPasswordInterface : ForgotPasswordInterface? = null
    var responseInterface: RetrofitRepository? = null
    private lateinit var token : String


    fun forgot(
        jsonObject: JsonObject,
        requireContext: Context,
        fragmentForgotPasswordBinding: FragmentForgotPasswordBinding
    ){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.forgotPassword(jsonObject).enqueue( object : Callback<DataModal> {
            override fun onFailure(call: Call<DataModal>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())

            }

            override fun onResponse(call: Call<DataModal>, response: Response<DataModal>) {
                if (response.code() == 200)
                {
                    responseInterface?.onSuccess(response.body()?.message.toString())
                    val constant : Constant.Companion?=null
                    constant?.getAcess(response.body()?.response?.accessToken.toString())
                    Toast.makeText(requireContext, "We sent an email to "+fragmentForgotPasswordBinding.emailET.text.toString()+", Please check your email for reset your password.",Toast.LENGTH_LONG).show()

                    fragmentForgotPasswordBinding.emailET.text?.clear()
                    fragmentForgotPasswordBinding.emailET.clearFocus()

                    Handler().postDelayed({

                        }, 800)



                }
                if(response.code() ==400)
                {
                    Toast.makeText(requireContext,"Email doesn't exist with us",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

