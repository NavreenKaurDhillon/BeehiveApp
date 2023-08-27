package com.bhive.beehiveapp.Fragments

import android.app.NotificationChannelGroup
import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentBuildingCalendarBinding
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.GetProfileViewModel
import com.bhive.beehiveapp.viewmodel.NotificationsViewModel


class BuildingCalendarFragment : BaseFragment() {

    private var i = 0
    var abc: ABC? = null
    private lateinit var getProfileViewModel: GetProfileViewModel
    private lateinit var notificationsViewModel: NotificationsViewModel
    var ColorPrimary : String ? = null
    var mainActivity = MainActivity()

    companion object {
        var DATA_LOADED = 0
    }

    lateinit var buildingCalendarBinding: FragmentBuildingCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildingCalendarBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_building_calendar, container, false)
        return buildingCalendarBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
            Constant.PRIMARY_COLOR = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }

        setColor()

//        abc?.displayProgress()

        getProfileViewModel = ViewModelProvider(this).get(GetProfileViewModel::class.java)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        notificationsViewModel.getNotificationsCount()
//        mainActivity.setBadges()



        if(SharedPreferencesClass.getString(requireContext(),"token")!=null
        )
        {
            if (Constant.ACCESS_TOKEN == null)
            {
                Constant.ACCESS_TOKEN = SharedPreferencesClass.getString(requireContext(),"token")
            }

        }
        else{
            findNavController().navigate(R.id.action_buildingCalendarFragment_to_loginFragment2)
        }


        getProfileViewModel.userProfile()
        buildingCalendarBinding.headingLayout.visibility = View.GONE

        (baseActivity.onLogout(object : ABC {
            override fun displayProgress() {
                buildingCalendarBinding.progressBar.visibility = View.VISIBLE
            }

            override fun hideProgress() {
                buildingCalendarBinding.progressBar.visibility = View.GONE
            }
        }))
        if (DATA_LOADED == 0) {
            Handler().postDelayed({
                findNavController().navigate(R.id.calendarEventsFragment)

            }, 2000)
        } else {
            findNavController().navigate(R.id.calendarEventsFragment)

        }
    }

    private fun setColor() {
        buildingCalendarBinding.progressBar.indeterminateTintList = (ColorStateList.valueOf(android.graphics.Color.parseColor(ColorPrimary)))

    }
}


open interface ABC {
        fun displayProgress()
        fun hideProgress()
    }

