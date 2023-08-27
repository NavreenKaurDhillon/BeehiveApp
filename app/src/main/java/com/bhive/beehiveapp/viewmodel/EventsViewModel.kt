package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.Fragments.BuildingCalendarFragment
import com.bhive.beehiveapp.Fragments.CalendarEventsFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.Fragments.SplashFragment
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentCalenderBinding
import com.bhive.beehiveapp.models.CalendarEvents
import com.bhive.beehiveapp.models.EventsDetails
import com.bhive.beehiveapp.models.MonthlyCalendarEvents
import com.bhive.beehiveapp.models.MonthlyEventsDetails
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class EventsViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    var jsonObject: JsonObject = JsonObject()
     var arrayList = ArrayList<CalendarEvents>()
    var calendarEventsFragment = CalendarEventsFragment()
    var splashFragment = SplashFragment()

    fun getEvents(
        startDate: Long,
        endDate: Long,
        fragmentCalenderBinding: FragmentCalenderBinding,
        selectedDateTime: String
    ) {

        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getEventsList(Constant.SOCIETY_ID.toString(), startDate, endDate).enqueue(object : Callback<EventsDetails> {
            override fun onFailure(call: Call<EventsDetails>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
                Log.d(TAG, "onFailure: ////////" + t.toString())
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<EventsDetails>, response: Response<EventsDetails>) {
                if (response.code() == 200) {
                    CalendarEventsFragment.dataFetched = 1
                    val records = response.body()?.response
                    val events_list = ArrayList<CalendarEvents>()

                    Log.d(TAG, "onResponse: ....." + response.body()?.response.toString())
                    if (records != null) {
                        for (eventsData in records) {

                            events_list.add(
                                CalendarEvents(
                                    eventsData.agenda,
                                    eventsData.description
                                )
                            )
                        }
                        if(events_list.size == 0)
                        {

                            fragmentCalenderBinding.notFound.text ="No events found for selected date \n"+selectedDateTime
                            fragmentCalenderBinding.notFound.visibility= View.VISIBLE
                            fragmentCalenderBinding.recyclerView.visibility=View.GONE
                        }
                        else{
                            fragmentCalenderBinding.notFound.visibility= View.GONE
                            fragmentCalenderBinding.recyclerView.visibility= View.VISIBLE
                            calendarEventsFragment.setEventsAdapter(
                                events_list,
                                fragmentCalenderBinding
                            )
                        }
                        Log.d(TAG, "" +
                                "onResponse: /// agenda" + events_list)

                    }

                }
            }
        })
    }

    fun getMonthlyEvents(
        currentMonth: Int,
        fragmentCalenderBinding: FragmentCalenderBinding,
        requireContext: Context,
        view: View,
    ): ArrayList<MonthlyCalendarEvents> {
        
        val events_list = ArrayList<MonthlyCalendarEvents>()

        Log.d(TAG, "getMonthlyEvents:  month is ////"+currentMonth)
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)

        retIn.getMonthlyEventsList(Constant.SOCIETY_ID.toString(), currentMonth).enqueue(object : Callback<MonthlyEventsDetails>
        {
            override fun onFailure(call: Call<MonthlyEventsDetails>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
                Log.d(TAG, "onFailure: ////////" + t.toString())
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<MonthlyEventsDetails>, response: Response<MonthlyEventsDetails>) {
                if (response.code() == 200) {
                    val records = response.body()?.response


                    Log.d(TAG, "onResponse: ..... /////////" + response.body()?.response.toString())
                    if (records != null) {
                        for (eventsData in records) {

                            events_list.add(MonthlyCalendarEvents(eventsData.eventDate.toLong())
                            )
//                            events_list.add(
//                                records.
//                                )

                        }
                        Log.d(TAG, "onResponse: /// event listt in view model"+events_list)
                        calendarEventsFragment.setEventsDisplay(events_list , fragmentCalenderBinding)

//                        CalendarEventsFragment.DATA_LOADED = 1
                        BuildingCalendarFragment.DATA_LOADED = 1
                    }
                }
                if (response.code() == 401){
                    Constant.ACCESS_TOKEN = null
                    SharedPreferencesClass.saveString(requireContext,"token",null)
                    Log.d(TAG, "onResponse: ///// "+ (response.body()?.message ))
                    Toast.makeText(requireContext , " Session expired", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigate(R.id.action_calendarEventsFragment_to_loginFragment2)
                }
            }
        })
        return events_list
    }
}





