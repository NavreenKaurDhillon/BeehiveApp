package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.ProfilePageFragment
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentProfilePageBinding
import com.bhive.beehiveapp.models.ProfileDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProfileViewModel : ViewModel() {
    
    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject

    fun userProfile() {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getProfile().enqueue(object : Callback<ProfileDetails> {
            override fun onFailure(call: Call<ProfileDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ProfileDetails>, response: Response<ProfileDetails>) {

                if (response.code() == 200)
                {
                  Constant.FLOOR_NO = response.body()?.response?.floorNumber.toString()
                  Constant.BUILDING_NAME = response.body()?.response?.society
                  Constant.FIRST_NAME = response.body()?.response?.firstName
                    ProfilePageFragment.FIRST_NAME = response.body()?.response?.firstName.toString()
                    Log.d(TAG, "onResponse: /// FiTSR NNME = "+Constant.FIRST_NAME)
                  Constant.LAST_NAME = response.body()?.response?.lastName.toString()
                  Constant.PHONE = response.body()?.response?.phone.toString()
                  Constant.EMAIL = response.body()?.response?.email.toString()
                  Constant.SOCIETY_LOGO = response.body()?.response?.logo
                  Constant.BUILDING_LOGO = response.body()?.response?.logo
                  Constant.SOCIETY_NAME = response.body()?.response?.society
                }
            }
        })

    }

    fun setUserProfile(fragmentProfilePageBinding: FragmentProfilePageBinding) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getProfile().enqueue(object : Callback<ProfileDetails> {
            override fun onFailure(call: Call<ProfileDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ProfileDetails>, response: Response<ProfileDetails>) {

                if (response.code() == 200)
                {
                  fragmentProfilePageBinding.firstNameET.setText(response.body()?.response?.firstName.toString())
                  fragmentProfilePageBinding.lastNameET.setText(response.body()?.response?.lastName.toString())
                  fragmentProfilePageBinding.emailET.setText(response.body()?.response?.email.toString())
                  fragmentProfilePageBinding.phoneNumberET.setText(response.body()?.response?.phone.toString())
                }
            }
        })

    }
}


