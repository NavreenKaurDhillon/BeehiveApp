package com.bhive.beehiveapp.viewmodel

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.Fragments.BaseFragment
import com.bhive.beehiveapp.Fragments.ReportAnIssueFragment
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.AddIssueDetails
import com.bhive.beehiveapp.models.UpdateIssueModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddIssueViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    lateinit var jsonObject: JsonObject
    var baseFragment = BaseFragment()

    fun addIssue(
        imagesSent: Int,
        title: RequestBody?,
        description: RequestBody?,
        image1: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        image3: MultipartBody.Part?,
        image4: MultipartBody.Part?,
        image5: MultipartBody.Part?,
        view: View,
        dialog: Dialog,
        requireContext: Context
    ) {

        Log.d(TAG, "addIssue: /////// multiparrt  title"+title.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  des"+description.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image1.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image2.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image3.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image4.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image5.toString())

        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)

        retIn.addNewIssue(Constant.SOCIETY_ID.toString() ,title , description, image1,
                image2, image3, image4, image5
            ).enqueue(object : Callback<AddIssueDetails> {
                override fun onResponse(call: Call<AddIssueDetails>, response: Response<AddIssueDetails>) {
                    if (response.code() == 200) {
                        Toast.makeText(requireContext, ""+response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        ReportAnIssueFragment.ADDED_SUCCESS = 1
                        Navigation.findNavController(view).navigate(R.id.reportAnIssueMainFragment)
                        responseInterface?.onSuccess(response.body().toString())
//                        dialog.show()
                        Log.d(TAG, "onResponse: ///"+response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AddIssueDetails>, t: Throwable) {
                    Log.d(TAG, "onFailure: ////"+t.message.toString())
                    responseInterface?.onFailure(t.message.toString())
                }

            })
    }

    fun updateIssue(
        issueId: String,
        title: RequestBody?,
        description: RequestBody?,
        image1: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        image3: MultipartBody.Part?,
        image4: MultipartBody.Part?,
        image5: MultipartBody.Part?,
        filesArray: RequestBody?,
        view: View
    ) {

        Log.d(TAG, "addIssue: /////// multiparrt  title"+title.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  des"+description.toString())
        Log.d(TAG, "addIssue: /////// multiparrt  images"+image1.toString())
//        Log.d(TAG, "addIssue: /////// multiparrt  images"+image2.toString())
//        Log.d(TAG, "addIssue: /////// multiparrt  images"+image3.toString())
//        Log.d(TAG, "addIssue: /////// multiparrt  images"+image4.toString())
//        Log.d(TAG, "addIssue: /////// multiparrt  images"+image5.toString())

        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)

        retIn.updateIssue(Constant.SOCIETY_ID.toString(),issueId.toString() ,filesArray, title , description, image1, image2).enqueue(object : Callback<UpdateIssueModelClass> {
                override fun onResponse(call: Call<UpdateIssueModelClass>, response: Response<UpdateIssueModelClass>) {
                    if (response.code() == 200) {
                        responseInterface?.onSuccess(response.body()?.message.toString())
                                         Toast.makeText(view.context, "Issue updated successfully. ",Toast.LENGTH_LONG).show()

//                        Navigation.findNavController(view).navigate(R.id.reportAnIssueMainFragment)
                        ReportAnIssueFragment.UPDATED_SUCCESS = 1
                        responseInterface?.onSuccess(response.body().toString())
//                        dialog.show()
                        Log.d(TAG, "onResponse: ///"+response.body().toString())
                    }
                }

                override fun onFailure(call: Call<UpdateIssueModelClass>, t: Throwable) {
                    Log.d(TAG, "onFailure: ////"+t.message.toString())
                    responseInterface?.onFailure(t.message.toString())
                }

            })
    }
}



