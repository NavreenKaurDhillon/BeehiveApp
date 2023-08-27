package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.StatusIssuesFragment
import com.bhive.beehiveapp.adapters.BuildingIssuesAdapter
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentIssuesBinding
import com.bhive.beehiveapp.databinding.FragmentNotificationsBinding
import com.bhive.beehiveapp.models.IssuesDetails
import com.bhive.beehiveapp.models.IssuesModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetIssuesViewModel : ViewModel() {
    private var buildingIssuesAdapter = BuildingIssuesAdapter()
    var responseInterface : RetrofitRepository ? = null
    var arrayList = ArrayList<IssuesModelClass>()
    var limit : Int ?= null

    fun getIssuesWithStatus(status: Int, societyId: String, fragmentIssuesBinding: FragmentIssuesBinding, PAGE: Int){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getIssues(societyId.toString(),status, PAGE , 2).enqueue(object  : Callback<IssuesDetails>{
            override fun onResponse(call: Call<IssuesDetails>, response: Response<IssuesDetails>) {
                if (response.code() == 200){
                    if(arrayList!=null){
                        StatusIssuesFragment.dataFetched2 =1

                    }
                    StatusIssuesFragment.dataFetched =1
                    fragmentIssuesBinding.progressBar.visibility = View.GONE
                    StatusIssuesFragment.LIMIT = response.body()?.meta?.totalPages

                    if(status ==1){
                        //open
                        fragmentIssuesBinding.notFound.text="No issues found"
                    }


                    val  records = response.body()?.response
                    val issuesList = ArrayList<IssuesModelClass>()
                    if (records!=null){
                        for (i in records){
                           issuesList.add(IssuesModelClass(i.complainant, i.title, i.description, i.status, i.createdAt,i.id))
                        }
                        arrayList = (arrayList+issuesList) as ArrayList<IssuesModelClass>
                        if(arrayList.size == 0)
                        {
                            if (StatusIssuesFragment.LIMIT==0)
                            {
                            fragmentIssuesBinding.notFound.visibility= View.VISIBLE
                            fragmentIssuesBinding.recyclerView2.visibility= View.GONE
                        }
                            fragmentIssuesBinding.notFound.visibility= View.VISIBLE
                            fragmentIssuesBinding.progressBar.visibility=View.GONE
                            fragmentIssuesBinding.recyclerView2.visibility= View.GONE

                        }
                        else{
                            fragmentIssuesBinding.notFound.visibility= View.GONE
                            fragmentIssuesBinding.progressBar.visibility= View.GONE
                            fragmentIssuesBinding.recyclerView2.visibility= View.VISIBLE
                            fragmentIssuesBinding.recyclerView2.adapter = buildingIssuesAdapter
                            buildingIssuesAdapter.submitList(arrayList)
                        }

                    }
                    Log.d(TAG, "onResponse: // aray"+issuesList)

                }

            }

            override fun onFailure(call: Call<IssuesDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

        })
    }
    fun getIssuesWithOwner(ownerType: Int, societyId: String, fragmentIssuesBinding: FragmentIssuesBinding, PAGE: Int){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getIssuesWithOwner(societyId.toString(),1, PAGE).enqueue(object  : Callback<IssuesDetails>{
            override fun onResponse(call: Call<IssuesDetails>, response: Response<IssuesDetails>) {
                if (response.code() == 200){
                    if(arrayList!=null){
                        StatusIssuesFragment.dataFetched2 =1

                    }
                    StatusIssuesFragment.dataFetched =1
                    StatusIssuesFragment.LIMIT = response.body()?.meta?.totalPages


                    if(ownerType ==2){
                        //by me
                        fragmentIssuesBinding.notFound.text="No issues found"
                    }

                    if(ownerType ==3){
                        //all level
                        fragmentIssuesBinding.notFound.text="No issues found"
                    }

                    val  records = response.body()?.response
                    val issuesList = ArrayList<IssuesModelClass>()
                    if (records!=null){
                        for (i in records){
                           issuesList.add(IssuesModelClass(i.complainant, i.title, i.description, i.status, i.createdAt,i.id))
                        }
                        arrayList = (arrayList+issuesList) as ArrayList<IssuesModelClass>
                        if(arrayList.size == 0)
                        {
                            if (StatusIssuesFragment.LIMIT==0){
                            fragmentIssuesBinding.notFound.visibility= View.VISIBLE
                            fragmentIssuesBinding.recyclerView2.visibility= View.GONE
                        }
                            fragmentIssuesBinding.notFound.visibility= View.VISIBLE
                            fragmentIssuesBinding.progressBar.visibility=View.GONE
                            fragmentIssuesBinding.recyclerView2.visibility= View.GONE

                        }
                        else{
                            fragmentIssuesBinding.notFound.visibility= View.GONE
                            fragmentIssuesBinding.progressBar.visibility= View.GONE
                            fragmentIssuesBinding.recyclerView2.visibility= View.VISIBLE
                            fragmentIssuesBinding.recyclerView2.adapter = buildingIssuesAdapter
                            buildingIssuesAdapter.submitList(arrayList)
                        }

                    }
                    Log.d(TAG, "onResponse: // array"+issuesList)


                }

            }

            override fun onFailure(call: Call<IssuesDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

        })
    }
}