package com.bhive.beehiveapp.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.Fragments.AllCalendarEventsFragment
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentAllCalendarEventsBinding
import com.bhive.beehiveapp.models.AllEvents
import com.bhive.beehiveapp.models.AllEventsModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllEventsViewModel : ViewModel() {
    var responseInterface: RetrofitRepository? = null
    var allEventsFragment : AllCalendarEventsFragment = AllCalendarEventsFragment()

    fun getAllEvents(
        id: String,
        allCalendarEventsBinding: FragmentAllCalendarEventsBinding,
        todayTimestamp: Long,
        allCalendarEventsBinding1: FragmentAllCalendarEventsBinding
    ) {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.getAllEvents(id , todayTimestamp).enqueue(object : Callback<AllEventsModelClass> {
            override fun onResponse(
                call: Call<AllEventsModelClass>,
                response: Response<AllEventsModelClass>
            ) {
                if (response.code() == 200)
                {
                    val records = response.body()?.response
                    var rules_list = ArrayList<AllEvents>()

                    if (response.body()?.response?.size  ==0)
                    {
                        allCalendarEventsBinding.notFound.visibility = View.VISIBLE
                    }
                    if (records != null) {
                        for (i in records){
                            rules_list.add(
                                AllEvents(i.agenda.toString(),
                                    i.description.toString(),
                                i.eventDate))
                        }
                        allEventsFragment.setA()
                        allEventsFragment.setAdapter(rules_list, allCalendarEventsBinding)
                    }
                }
            }
            override fun onFailure(call: Call<AllEventsModelClass>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }


        })
    }

}

