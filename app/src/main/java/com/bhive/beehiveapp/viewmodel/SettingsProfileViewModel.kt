package com.bhive.beehiveapp.viewmodel

import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.DrawerHeaderLayoutBinding
import com.bhive.beehiveapp.databinding.FragmentSettingsBinding
import com.bhive.beehiveapp.models.ProfileDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsProfileViewModel : ViewModel()
{
    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject
    var mainActivity = MainActivity()

    fun userProfile(binding: FragmentSettingsBinding) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getProfile().enqueue(object : Callback<ProfileDetails> {
            override fun onFailure(call: Call<ProfileDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<ProfileDetails>, response: Response<ProfileDetails>) {

                if (response.code() == 200)
                {
                    //done
                    responseInterface?.onSuccess(response.body()?.message.toString())
                    var record = response.body()?.response

                    if (record != null) {

//                        binding.iconTV.setText(record.firstName.substring(0,1))
//                        binding.nameTV.setText(record.firstName+" "+record.lastName)
//                        binding.buildingNameTV.setText(record.society)
//                        jsonObject =  JsonObject()
//                        jsonObject.addProperty("firstName",record.firstName)
//                        jsonObject.addProperty("phone",record.phone)
//
//                        mainActivity.getUserProfile(jsonObject)

                    }
                }
            }
        })

    }
}


