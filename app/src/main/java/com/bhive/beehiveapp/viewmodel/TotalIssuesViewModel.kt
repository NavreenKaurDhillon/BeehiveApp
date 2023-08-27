package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.StatusIssuesFragment
import com.bhive.beehiveapp.adapters.BuildingIssuesAdapter
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentIssuesBinding
import com.bhive.beehiveapp.databinding.FragmentReportAnIssueMainBinding
import com.bhive.beehiveapp.models.IssuesDetails
import com.bhive.beehiveapp.models.IssuesModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TotalIssuesViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    var jsonObject: JsonObject = JsonObject()
    var arrayList = ArrayList<IssuesModelClass>()
    private var buildingIssuesAdapter = BuildingIssuesAdapter()

    fun getTotalIssues(societyId: String, fragmentIssuesBinding: FragmentIssuesBinding, PAGE: Int) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        Log.d(TAG, "getTotalIssues: //// id is  = "+societyId)
        retIn.getTotalReportedIssues(societyId,2,PAGE).enqueue(object : Callback<IssuesDetails> {
            override fun onFailure(call: Call<IssuesDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
                Log.d(ContentValues.TAG, "onFailure: ////////" + t.toString())
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<IssuesDetails>, response: Response<IssuesDetails>) {
                val events_list = ArrayList<IssuesModelClass>()

                if (response.code() == 200) {
                    StatusIssuesFragment.LIMIT = response.body()?.meta?.totalPages
                    if (StatusIssuesFragment.LIMIT ==0)
                    {
                        fragmentIssuesBinding.notFound.text="No issues found"
                    }
                    fragmentIssuesBinding.progressBar.visibility = View.GONE



                    if (arrayList!=null)
                    {
                        StatusIssuesFragment.dataFetched2=1
                    }
                    StatusIssuesFragment.dataFetched = 1
                    val records = response.body()?.response

                    Log.d(
                        ContentValues.TAG,
                        "onResponse: ....." + response.body()?.response.toString()
                    )

                    if (records != null) {
                        for (data in records) {

                            events_list.add(
                                IssuesModelClass(
                                    data.complainant,
                                    data.title,
                                    data.description,
                                    data.status,
                                    data.createdAt,
                                    data.id
                                )
                            )
                        }
                        arrayList = (arrayList+events_list) as ArrayList<IssuesModelClass>
                        if(arrayList.size == 0 )
                        {
                            if (StatusIssuesFragment.LIMIT==0){
                                fragmentIssuesBinding.notFound.visibility= View.VISIBLE
                                fragmentIssuesBinding.recyclerView2.visibility= View.GONE
                            }
                            fragmentIssuesBinding.recyclerView2.visibility= View.VISIBLE

                        }
                        else{
                            fragmentIssuesBinding.notFound.visibility= View.GONE
                            fragmentIssuesBinding.progressBar.visibility= View.GONE
                            fragmentIssuesBinding.recyclerView2.visibility= View.VISIBLE
                            fragmentIssuesBinding.recyclerView2.adapter = buildingIssuesAdapter
                            buildingIssuesAdapter.submitList(arrayList)
                        }

                    }
                }
            }
        })
    }
}
