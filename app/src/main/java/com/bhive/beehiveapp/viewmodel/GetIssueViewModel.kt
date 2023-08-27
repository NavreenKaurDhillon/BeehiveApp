package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.content.ContentValues.TAG


import android.content.Context
import android.graphics.drawable.LevelListDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.ReportAnIssueFragment
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.LeftMessageAdapter
import com.bhive.beehiveapp.adapters.UserMessageAdapter
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.ActivityMainBinding.inflate
import com.bhive.beehiveapp.databinding.FragmentReportanissueBinding
import com.bhive.beehiveapp.models.GetIssueData
import com.bhive.beehiveapp.models.GetIssueDetails
import com.bhive.beehiveapp.models.Messages
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bhive.beehiveapp.databinding.DrawerHeaderLayoutBinding.inflate


class GetIssueViewModel : ViewModel() {
    private var jsonObject: JsonObject = JsonObject()
    var responseInterface: RetrofitRepository? = null
    var reportAnIssueFragment = ReportAnIssueFragment()
    val leftMessageAdapter = LeftMessageAdapter()
    val userMessageAdapter = UserMessageAdapter()

        fun getIssue(societyId:String,
            issueId: String,
            reportanissueBinding: FragmentReportanissueBinding, context: Context
        ){

        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getAnIssue( societyId, issueId).enqueue( object : Callback<GetIssueDetails> {
            override fun onFailure(call: Call<GetIssueDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<GetIssueDetails>, response: Response<GetIssueDetails>) {
                var jsonObject = JsonObject()
                if (response.code() == 200)
                {
                    responseInterface?.onSuccess(response.body()?.message.toString())
                   jsonObject.addProperty("issueId", response.body()?.response?.id )
                   jsonObject.addProperty("title", response.body()?.response?.title )
                   jsonObject.addProperty("description", response.body()?.response?.description.toString()  )
                   jsonObject.addProperty("complainant_id", response.body()?.response?.userId.toString()  )
                   jsonObject.addProperty("issue_id", response.body()?.response?.id.toString()  )
                   jsonObject.addProperty("floor", response.body()?.response?.floorNumber.toString()  )
                   jsonObject.addProperty("time", response.body()?.response?.createdAt.toString()  )
                   jsonObject.addProperty("issueStatus", response.body()?.response?.status.toString()  )
                   jsonObject.addProperty("messageSize",
                       response.body()?.response?.messages?.size
                   )

                    val response = response.body()?.response
                    val messagesList = response?.messages
                    val mList = ArrayList<GetIssueData>()
                    val userMessages = ArrayList<Messages>()
                    val otherMessages = ArrayList<Messages>()

                    if (messagesList!=null){

                    }
                    Log.d(TAG, "onResponse: /// res = "+ (response?.messages))
                    if (messagesList!=null){
                        for (i in messagesList){
                            userMessages.add(
                                Messages(i.id, i.message,
                                    i.sendBy, i.createdAt,
                                    i.isRead, i.sendByName)
                            )
                            reportanissueBinding.userChat.adapter = userMessageAdapter
                            Log.d(TAG, "onResponse: /// messages lsit sent tot user adaptern= "+userMessages)
                            userMessageAdapter.submitList(userMessages)
                            Log.d(TAG, "onResponse: // my id is "+Constant.USER_ID)
                            if (i.sendBy == Constant.USER_ID){

                                Log.d(TAG, "onResponse: //  me messgae = "+i.message+" send by = "+i.sendBy)


                            }
                            else{
//                                otherMessages.add(
//                                    Messages(i.id, i.message,
//                                        i.sendBy, i.createdAt,
//                                        i.isRead, i.sendByName)
//                                )
//                                Log.d(TAG, "onResponse: // other messgae = "+i.message+" send by = "+i.sendBy)
//                                reportanissueBinding.managerChat.adapter = leftMessageAdapter
//                                leftMessageAdapter.submitList(otherMessages)

                            }
                            userMessageAdapter.notifyDataSetChanged()
                            leftMessageAdapter.notifyDataSetChanged()


                        }

                    }


//                        if (response.userId == Constant.USER_ID){
//                            userMessages.add(Messages(response.id, response.messages[0]., response.send))
//                            reportanissueBinding.chatRecycler.adapter = userMessageAdapter
//                        userMessageAdapter.submitList(mList)
//                        }
//                        else{
//                            otherMessages.add(GetIssueData(response.id, response.societyId,
//                                response.referenceId, response.images, response.status , response.title,
//                                response.description, response.userId, response.complainant, response.createdAt,
//                                response.reportedDate, response.floorNumber, response.messages))
//                        }
//                        reportanissueBinding.chatRecycler.adapter = leftMessageAdapter
//                        leftMessageAdapter.submitList(mList)
//
//                    }
//                    if (response?.userId  == Constant.USER_ID)
//                    {
//                        reportanissueBinding.chatRecycler.adapter = userMessageAdapter
//                        userMessageAdapter.submitList(mList)
//                    }
//                    else{
//                        reportanissueBinding.chatRecycler.adapter = leftMessageAdapter
//                        leftMessageAdapter.submitList(mList)
//                    }

                    val jsonElements = Gson().toJsonTree(response?.images) as JsonArray
                    jsonObject.add("images", jsonElements)
                    Log.d(TAG, "onResponse: ///json in if"+jsonObject.get("images"))
                    reportAnIssueFragment.setData(jsonObject,reportanissueBinding, context   )

                }
            }
        })

        }
}

