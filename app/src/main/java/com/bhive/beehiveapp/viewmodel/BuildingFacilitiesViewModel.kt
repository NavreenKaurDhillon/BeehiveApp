package com.bhive.beehiveapp.viewmodel

import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.BuildingFacilitiesFragment
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentBuildingFacilitiesBinding
import com.bhive.beehiveapp.models.BuildingFacilties
import com.bhive.beehiveapp.models.BuildingFaciltiesModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuildingFacilitiesViewModel : ViewModel() {

    var buildingFacilitiesFragment = BuildingFacilitiesFragment()
    var responseInterface: RetrofitRepository? = null

    fun getBUildingFacilities(
        id: String,
        buildingFacilitiesBinding: FragmentBuildingFacilitiesBinding
    ) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getBuildingFacilities(id).enqueue(object : Callback<BuildingFaciltiesModelClass> {
            override fun onFailure(call: Call<BuildingFaciltiesModelClass>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(
                call: Call<BuildingFaciltiesModelClass>,
                response: Response<BuildingFaciltiesModelClass>
            ) {
                if (response.code() == 200) {
                    val records = response.body()?.response
                    var facilities_list = ArrayList<BuildingFacilties>()

                    if (response.body()?.response?.size  ==0)
                    {
                        Handler().postDelayed({
                            buildingFacilitiesBinding.notFound.visibility = View.VISIBLE

                        }, 1500)

                    }
                    if (records != null) {

                        for (data in records) {

                            facilities_list.add(
                                BuildingFacilties(
                                    data.name.toString(),
                                    data.location.toString(),
                                    data.openTime.toString(),
                                    data.closeTime.toString()
                                )
                            )
                        }
                        buildingFacilitiesFragment.setAdapter(facilities_list, buildingFacilitiesBinding)
                        responseInterface?.onSuccess(response.body()?.message.toString())

                    }
                }
            }
        })
    }
}