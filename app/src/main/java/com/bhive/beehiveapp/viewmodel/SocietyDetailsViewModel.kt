package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.RegisterFragment
import com.bhive.beehiveapp.Fragments.ReportAnIssueFragment
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentReportanissueBinding
import com.bhive.beehiveapp.models.GetBuildingDetails
import com.bhive.beehiveapp.models.GetIssueDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocietyDetailsViewModel  : ViewModel() {
    private var jsonObject: JsonObject = JsonObject()
    var responseInterface: RetrofitRepository? = null
    var reportAnIssueFragment = ReportAnIssueFragment()

    fun getBuildingDetails(
        buildingId: String) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getBuidlingDetails(buildingId).enqueue( object : Callback<GetBuildingDetails> {
            override fun onFailure(call: Call<GetBuildingDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<GetBuildingDetails>, response: Response<GetBuildingDetails>) {
                var jsonObject = JsonObject()
                if (response.code() == 200)
                {
                   ReportAnIssueFragment.BUILDING_NAME = response.body()?.response?.name!!
//                   reportanissueBinding.buildingName.setText(response.body()?.response?.name!!)
                }
            }
        })

    }
}

