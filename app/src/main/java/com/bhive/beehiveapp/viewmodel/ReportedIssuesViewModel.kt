package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.ReportAnIssueMainFragment
import com.bhive.beehiveapp.Fragments.StatusIssuesFragment
import com.bhive.beehiveapp.adapters.BuildingIssuesAdapter
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentReportAnIssueMainBinding
import com.bhive.beehiveapp.interfaces.ReportAnIssueInterface
import com.bhive.beehiveapp.models.*
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportedIssuesViewModel  : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    var jsonObject: JsonObject = JsonObject()
    var arrayList = ArrayList<IssuesModelClass>()
    private var buildingIssuesAdapter = BuildingIssuesAdapter()

    fun getIssues(
        fragmentReportAnIssueMainBinding: FragmentReportAnIssueMainBinding,
        societyId: String?
    ) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getReportedIssues(societyId.toString()).enqueue(object : Callback<IssuesDetails> {
            override fun onFailure(call: Call<IssuesDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<IssuesDetails>, response: Response<IssuesDetails>) {
                val events_list = ArrayList<IssuesModelClass>()

                if (response.code() == 200) {
                    val records = response.body()?.response

                    fragmentReportAnIssueMainBinding.total.setText(response.body()!!.totalIssuesCount.toString())
                    Constant.OPEN_ISSUES = response.body()!!.openIssuesCount
                    Constant.MY_ISSUES = response.body()!!.myIssuesCount
                    Constant.TOTAL_ISSUES = response.body()!!.totalIssuesCount
                    Log.d(TAG, "onResponse: /// viewmodel value set "+Constant.TOTAL_ISSUES)
                    Log.d(TAG, "onResponse: /// viewmodel value set "+ response.body()!!.totalIssuesCount)

                    fragmentReportAnIssueMainBinding.open.setText(response.body()!!.openIssuesCount.toString())

                    //1
//                    fragmentReportAnIssueMainBinding.resolved.setText(response.body()!!.myIssuesCount.toString())
//                    Constant.MY_ISSUES = response.body()!!.myIssuesCount

                    fragmentReportAnIssueMainBinding.progress.setText(response.body()!!.myIssuesCount.toString())
                    Constant.ALL_LEVEL_ISSUES = response.body()!!.totalIssuesCount

                    if (records != null) {
                        for (data in records) {

                            events_list.add(
                                IssuesModelClass(
                                    data.complainant,
                                    data.title,
                                    data.description,
                                    data.status,
                                    data.createdAt,
                                    data.id))
                        }
                        Log.d(ContentValues.TAG, "onResponse: /// agenda" + events_list)
//                        arrayList = events_list
//                        fragmentReportAnIssueMainBinding.recyclerView2.adapter = buildingIssuesAdapter
//                        Log.d(ContentValues.TAG, "onViewCreated: id is ////" + fragmentReportAnIssueMainBinding.recyclerView2)
//                        buildingIssuesAdapter.submitList(arrayList)
                    }
                }
            }
        })
    }
}
