package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.RegisterFragment
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.*
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocietiesViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject : JsonObject
    var registerFragment = RegisterFragment()

    fun getSocieties(spinner: Spinner,context : Context)
    {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getSocitiesList().enqueue( object : Callback<SocietiesDetails> {
            override fun onFailure(call: Call<SocietiesDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())

            }
            override fun onResponse(call: Call<SocietiesDetails>, response: Response<SocietiesDetails>) {
                if (response.code() == 200) {
                    val records = response.body()?.data

                    if (records != null) {
                        var socities_list : ArrayList<SpinnerItemsModelClass> = ArrayList<SpinnerItemsModelClass>()
                        socities_list.add(
                            SpinnerItemsModelClass(
                                "Select building",
                                R.drawable.ic_building, 0, 0
                            ))
                        for (socitiesdata in records) {

                                socities_list.add(
                                    SpinnerItemsModelClass(
                                        socitiesdata.name.toString(),
                                        R.drawable.ic_building,
                                        socitiesdata.id,
                                        socitiesdata.floors
                                    )
                                )
                            }

                        registerFragment.setData(context,socities_list,spinner)
                        Log.d(TAG, "onResponse: fetched"+socities_list)


                    }
                }
            }


        })
    }

}


