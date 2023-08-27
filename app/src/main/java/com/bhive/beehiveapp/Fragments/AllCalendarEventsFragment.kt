package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.AllEventsAdapter
import com.bhive.beehiveapp.databinding.FragmentAllCalendarEventsBinding
import com.bhive.beehiveapp.models.AllEvents
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.AllEventsViewModel

class AllCalendarEventsFragment : BaseFragment() {

    private lateinit var allCalendarEventsBinding : FragmentAllCalendarEventsBinding
    lateinit var allEventsViewModel : AllEventsViewModel
    var adapter = AllEventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allCalendarEventsBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_all_calendar_events , container,
        false)
        return allCalendarEventsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allEventsViewModel = ViewModelProvider(this).get(AllEventsViewModel::class.java)
        val tsLong = (System.currentTimeMillis() / 1000)*1000
        val ts = tsLong.toString()
        Log.d(TAG, "onViewCreated: /// current timedta,p = "+ts)
        allEventsViewModel.getAllEvents(Constant.SOCIETY_ID.toString()
        , allCalendarEventsBinding, ts.toLong() , allCalendarEventsBinding)

        allCalendarEventsBinding.backIcon.setOnClickListener{
            findNavController().navigate(R.id.action_allCalendarEventsFragment_to_buildingCalendarFragment)
        }
    }

    fun setAdapter(rulesList: ArrayList<AllEvents> , allCalendarEventsBinding: FragmentAllCalendarEventsBinding) {

        Log.d(TAG, "setAdapter: /// rules list here= "+rulesList)
        Log.d(TAG, "setAdapter: /// recycler  list here= "+allCalendarEventsBinding.allEventsRecycler)
        Log.d(TAG, "setAdapter: /// binding here= "+allCalendarEventsBinding)
        allCalendarEventsBinding.allEventsRecycler.adapter = adapter
        adapter.submitList(rulesList)

    }

    fun setA() {
        Log.d(TAG, "setA: /// here A is called ")
    }
}