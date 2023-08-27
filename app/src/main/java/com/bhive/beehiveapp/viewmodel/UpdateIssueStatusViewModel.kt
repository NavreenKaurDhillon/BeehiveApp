package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.MessageStatus
import com.bhive.beehiveapp.models.UpdateIssueStatusModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateIssueStatusViewModel  : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject

    fun updateMessageStatus(id : String, jsonObject: JsonObject) {
        Log.d(TAG, "updateMessageStatus: /// status = "+jsonObject)
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.updateIssueStatus(Constant.SOCIETY_ID.toString() , id, jsonObject).enqueue(
            object : Callback<UpdateIssueStatusModelClass> {
            override fun onFailure(call: Call<UpdateIssueStatusModelClass>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<UpdateIssueStatusModelClass>, response: Response<UpdateIssueStatusModelClass>) {

                if (response.code() == 200)
                {
                    Log.d(TAG, "onResponse: ///status updated")
//                    Toast.makeText(context , response.message().toString(), Toast.LENGTH_LONG).show()
                    //done
                }
            }
        })

    }
}
