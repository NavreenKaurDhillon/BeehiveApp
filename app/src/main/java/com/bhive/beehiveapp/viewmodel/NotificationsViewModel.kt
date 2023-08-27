package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.CalendarEventsFragment
import com.bhive.beehiveapp.Fragments.NotificationsFragment
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentCalenderBinding
import com.bhive.beehiveapp.databinding.FragmentNotificationsBinding
import com.bhive.beehiveapp.models.*
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel : ViewModel() {

    var count : Int ? = null
    var responseInterface: RetrofitRepository? = null
    var jsonObject: JsonObject = JsonObject()
    var notificationsFragment = NotificationsFragment()
    var notifications_list2 = ArrayList<Notifications>()
     var responseList= ArrayList<Notifications>()
    var mainActivity = MainActivity()


    fun getNotificationsList(notificationsBinding: FragmentNotificationsBinding): ArrayList<Notifications> {

        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getNotifications().enqueue(object : Callback<NotificationsDetails> {
            override fun onFailure(call: Call<NotificationsDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
                Log.d(ContentValues.TAG, "onFailure: ////////" + t.toString())
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<NotificationsDetails>,
                response: Response<NotificationsDetails>
            ) {
                if (response.code() == 200) {
                    val records = response.body()?.response
                    var notifications_list = ArrayList<Notifications>()


                    Log.d(
                        ContentValues.TAG,
                        "onResponse: ....." + response.body()?.response.toString()
                    )
                    if (records != null) {

                        for (data in records) {

                            notifications_list2.add(
                                Notifications(
                                    data.title.toString(),
                                    data.message.toString(),
                                    data.sourceId.toString(),
                                    data.type!!.toInt()
                                )
                            )
                            Log.d(TAG, "onResponse: ...>>>" + notifications_list2)
                        }
                        if(notifications_list2.size == 0)
                        {
                            Handler().postDelayed({

                                notificationsBinding.notFound.visibility= View.VISIBLE
                            }, 1500)

                            notificationsBinding.recyclerView.visibility= View.GONE
                        }
                        else{
                            notificationsBinding.notFound.visibility= View.GONE
                            notificationsBinding.recyclerView.visibility= View.VISIBLE
                            notificationsFragment.setAdapter(
                                notifications_list2,
                                notificationsBinding
                            )
                        }

                        Log.d(TAG, "onResponse: /// agenda" + notifications_list2)

                    }
                    Log.d(TAG, "onResponse: ...>>>" + notifications_list2)
                }
                Log.d(TAG, "onResponse: ...>>>" + notifications_list2)
                responseList = notifications_list2
                Log.d(TAG, "onResponse: /// repsonse list is "+responseList)
                Constant.updatedNotificationsList= responseList
            }
        })
        Log.d(TAG, "getNotificationsList: ///" + responseList)
        return responseList
    }

        fun deleteANotification(
            id: String
        ): ArrayList<Notifications> {
            val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
            retIn.deleteNotification( id).enqueue( object : Callback<NotificationsDetails> {
                override fun onFailure(call: Call<NotificationsDetails>, t: Throwable) {
                    responseInterface?.onFailure(t.message.toString())

                }

                override fun onResponse(call: Call<NotificationsDetails>, response: Response<NotificationsDetails>) {
                    if (response.code() == 200)
                    {
                        responseInterface?.onSuccess(response.body()?.message.toString())
                        Log.d(TAG, "onResponse: /// deletdnot")
//                        Toast.makeText(requireContext,""+ response.body()?.message.toString(),Toast.LENGTH_LONG).show()

                    }
                }
            })
            Log.d(TAG, "deleteANotification: ///response list "+responseList)
            return this.responseList
        }

        fun markNotificationsRead(requireContext : Context){
            val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
            retIn.notificationsRead().enqueue( object : Callback<NotificationsReadModelClass> {

                override fun onFailure(call: Call<NotificationsReadModelClass>, t: Throwable) {
                    responseInterface?.onFailure(t.message.toString())

                }

                override fun onResponse(call: Call<NotificationsReadModelClass>, response: Response<NotificationsReadModelClass>) {
                    if (response.code() == 200)
                    {
                        responseInterface?.onSuccess(response.body()?.message.toString())
//                        Toast.makeText(requireContext,""+ response.body()?.message.toString(),Toast.LENGTH_LONG).show()

                    }
                }

            })
        }

     fun getNotificationsCount() : Int? {
            val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
            retIn.getNotificationsCount().enqueue( object : Callback<NotificationsCountModelClass> {

                override fun onFailure(call: Call<NotificationsCountModelClass>, t: Throwable) {
                    responseInterface?.onFailure(t.message.toString())
                }
                override fun onResponse(call: Call<NotificationsCountModelClass>, response: Response<NotificationsCountModelClass>) {
                    if (response.code() == 200)

                    {  count = response.body()?.response?.toInt()
                        mainActivity.setBadges()
                        Log.d(TAG, "onResponse: /// count = "+response.body()?.response.toString())
                        MainActivity.count = response.body()?.response.toString()
                        Log.d(TAG, "onResponse: // in main = "+ MainActivity.count)
                        responseInterface?.onSuccess(response.body()?.message.toString())
                //                        Toast.makeText(requireContext,""+ response.body()?.message.toString(),Toast.LENGTH_LONG).show()

                    }
                }

            }
            )
         return count
        }



    }


