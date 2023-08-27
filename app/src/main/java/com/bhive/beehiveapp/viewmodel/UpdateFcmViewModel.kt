package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.UpdateIssueStatusModelClass
import com.bhive.beehiveapp.models.UpdateTokenModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFcmViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject

    fun updateFcmToken( jsonObject: JsonObject) {
        Log.d(ContentValues.TAG, "update token : ///== "+jsonObject)
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.updateFcmToken(jsonObject).enqueue(
            object : Callback<UpdateTokenModelClass> {
                override fun onFailure(call: Call<UpdateTokenModelClass>, t: Throwable)
                {
                    responseInterface?.onFailure(t.message.toString())
                }

                override fun onResponse(call: Call<UpdateTokenModelClass>, response: Response<UpdateTokenModelClass>) {

                    if (response.code() == 200)
                    {
                        Log.d(ContentValues.TAG, "onResponse: /// fcm token updated = "+jsonObject)
//                    Toast.makeText(context , response.message().toString(), Toast.LENGTH_LONG).show()
                        //done
                    }
                }
            })

    }
}
