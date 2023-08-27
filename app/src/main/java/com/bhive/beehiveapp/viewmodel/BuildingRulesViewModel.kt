package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.BuildingFacilitiesFragment
import com.bhive.beehiveapp.Fragments.BuildingRulesFragment
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentBuildingFacilitiesBinding
import com.bhive.beehiveapp.databinding.FragmentBuildingRulesBinding
import com.bhive.beehiveapp.models.BuildingFacilties
import com.bhive.beehiveapp.models.BuildingFaciltiesModelClass
import com.bhive.beehiveapp.models.BuildingRules
import com.bhive.beehiveapp.models.BuildingRulesModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildingRulesViewModel : ViewModel() {

    var buildingRulesFragment = BuildingRulesFragment()
    var responseInterface: RetrofitRepository? = null

    fun getBUildingRules(
        id: String,
        buildingRulesBinding: FragmentBuildingRulesBinding
    ) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getBuildingRules(id).enqueue(object : Callback<BuildingRulesModelClass> {
            override fun onFailure(call: Call<BuildingRulesModelClass>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<BuildingRulesModelClass>,
                response: Response<BuildingRulesModelClass>
            ) {
                if (response.code() == 200) {
                    val records = response.body()?.response
                    var rules_list = ArrayList<BuildingRules>()

                    if (response.body()?.response?.size  == 0)
                    {
                        Handler().postDelayed({
                            buildingRulesBinding.notFound.visibility = View.VISIBLE
                        }, 1300)

                    }
                    Log.d(TAG, "onResponse: /// rules response")
                    if (records != null) {

                        for (i in records){
                            rules_list.add(
                                BuildingRules(i.rule.toString(),
                            i.description.toString()))
                        }


                        buildingRulesFragment.setAdapter(rules_list , buildingRulesBinding)
                        }


                    }
                }



        })
    }
}