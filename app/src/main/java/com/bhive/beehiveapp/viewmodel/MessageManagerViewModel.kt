package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.MessageManagerDetails
import com.bhive.beehiveapp.models.MessageStatus
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageManagerViewModel  : ViewModel() {

    var responseInterface: RetrofitRepository? = null

    fun messageToManager(id: String, jsonObjectM: JsonObject) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.sendMessageToManager(id, jsonObjectM).enqueue(object : Callback<MessageManagerDetails> {
            override fun onFailure(call: Call<MessageManagerDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<MessageManagerDetails>, response: Response<MessageManagerDetails>) {

                if (response.code() == 200)
                {
                    //done
                    var record = response.body()?.message
                    Log.d(ContentValues.TAG, "onResponse: /// message is "+ record)

                }
            }
        })

    }
}
