package com.bhive.beehiveapp.viewmodel

import android.content.Context
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.Fragments.ABC
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.ProfileDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileViewModel : ViewModel() {

    var abc : ABC? =null
    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject

    fun updateUserProfile(jsonObject: JsonObject, context: Context, view: View) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.updateProfile(jsonObject).enqueue(object : Callback<ProfileDetails> {
            override fun onFailure(call: Call<ProfileDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<ProfileDetails>, response: Response<ProfileDetails>) {

                if (response.code() == 200)
                {
                    Constant.PHONE = response.body()?.response?.phone.toString()
                    SharedPreferencesClass.saveString(context,"lastName", response.body()?.response?.lastName.toString())
                    SharedPreferencesClass.saveString(context,"firstName", response.body()?.response?.firstName.toString())

                    Toast.makeText(context, "Profile updated successfully.",Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.settingsFragment)
                    responseInterface?.onSuccess(response.body()?.message.toString())

                    }
                }
            })
        }

    }



