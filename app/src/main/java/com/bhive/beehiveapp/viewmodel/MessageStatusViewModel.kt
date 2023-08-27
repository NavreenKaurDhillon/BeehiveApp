package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.MessageStatus
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageStatusViewModel  : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject

    fun updateMessageStatus(id : String) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.messageRead(id).enqueue(object : Callback<MessageStatus> {
            override fun onFailure(call: Call<MessageStatus>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<MessageStatus>, response: Response<MessageStatus>) {

                if (response.code() == 200)
                {
                    //done
                    var record = response.body()?.message
                    Log.d(TAG, "onResponse: /// message is "+ record)
                }
            }
        })

    }
}
