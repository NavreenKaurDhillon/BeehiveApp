package com.bhive.beehiveapp.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.DataModal
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    private lateinit var access_token : String


    fun signIn(jsonObject: JsonObject, view : View){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.signInUser(jsonObject).enqueue( object : Callback<DataModal> {
            override fun onFailure(call: Call<DataModal>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())

            }
            override fun onResponse(call: Call<DataModal>, response: Response<DataModal>) {
                val constant : Constant.Companion?=null

                if (response.code() == 200) {

                    responseInterface?.onSuccess(response.body()?.message.toString())
                    constant?.getAcess(response.body()?.response?.accessToken.toString())

                }
            }
        })
    }

}

