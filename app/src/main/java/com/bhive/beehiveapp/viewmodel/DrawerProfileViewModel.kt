package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.DrawerHeaderLayoutBinding
import com.bhive.beehiveapp.models.ProfileDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrawerProfileViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject
    var mainActivity = MainActivity()

    fun userProfile(context: Context, drawerBinding: DrawerHeaderLayoutBinding) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getProfile().enqueue(object : Callback<ProfileDetails> {
            override fun onFailure(call: Call<ProfileDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<ProfileDetails>, response: Response<ProfileDetails>) {

                if (response.code() == 200)
                {
                    Log.d(TAG, "onResponse: /// soicetyt name = "+ (response.body()?.response?.society
                        ))
                    Log.d(TAG, "onResponse: /// soicetyt logo = "+ (response.body()?.response?.logo
                        ))
                 drawerBinding.buildingName.setText(response.body()?.response?.society.toString() )
                    drawerBinding.mainLl.setBackgroundColor(Color.parseColor(response.body()?.response?.primaryColor))

                    //done
                    responseInterface?.onSuccess(response.body()?.message.toString())
                    Glide
                        .with(context)
                        .load(Constant.IMAGE_BASE_URL+ (response.body()?.response?.logo ))
                        .centerCrop()
                        .placeholder(R.drawable.ic_building)
                        .into(drawerBinding.header)


                }
            }
        })

    }
}


