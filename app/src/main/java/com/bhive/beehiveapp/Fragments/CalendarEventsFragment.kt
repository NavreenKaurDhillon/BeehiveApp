package com.bhive.beehiveapp.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.bhive.beehiveapp.MyFirebaseMessagingService
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.CalendarEventsAdapter
import com.bhive.beehiveapp.databinding.FragmentCalenderBinding
import com.bhive.beehiveapp.models.CalendarEvents
import com.bhive.beehiveapp.models.MonthlyCalendarEvents
import com.bhive.beehiveapp.viewmodel.EventsViewModel
import com.google.gson.JsonObject
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class CalendarEventsFragment  : BaseFragment() {

    var myFirebaseMessagingService = MyFirebaseMessagingService()

    companion object
    {
        var dataFetched : Int =0
        val shape : GradientDrawable  =  GradientDrawable()

    }
   private  lateinit var fragmentCalenderBinding: FragmentCalenderBinding
    private val adapter = CalendarEventsAdapter()
    var abc : ABC? = null
    var eventsList = ArrayList<MonthlyCalendarEvents> ()
    lateinit var eventsViewModel: EventsViewModel
    @RequiresApi(Build.VERSION_CODES.M)
    var arrayList = ArrayList<CalendarEvents>()
    lateinit var jsonObject: JsonObject
    var ColorPrimary : String ? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCalenderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false)
        return fragmentCalenderBinding.root
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }

        shape.shape = GradientDrawable.OVAL
        shape.setSize(10 , 10)
        shape.setColor(Color.parseColor(ColorPrimary))


        val unwrappedDrawable = AppCompatResources.getDrawable(requireContext()!!, R.drawable.color_primary_dot)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(ColorPrimary))
        val c = Calendar.getInstance(
        )
        var monthValue : Int = (c.get(Calendar.MONTH))+1
        if (SharedPreferencesClass.getString(requireContext(), "token")!=null)
        {
            eventsList = eventsViewModel.getMonthlyEvents((c.get(Calendar.MONTH))+1 , fragmentCalenderBinding, requireContext(), view)

        }

        setColor()
        Handler().postDelayed({

            if (dataFetched == 1){
                abc?.hideProgress()
                dataFetched = 0
            }
        }, 2000)


        var token = myFirebaseMessagingService.getToken(requireContext())
        Log.d(TAG, "onCreate: ******** token = "+token)
        val nf: NumberFormat = NumberFormat.getInstance()
        nf.setMinimumIntegerDigits(2)
        val selectedDateTime : String = (nf.format(c.get(Calendar.MONTH)+1).toString() +"/"+
               nf.format(c.get(Calendar.DATE)).toString()+"/"+
                c.get(Calendar.YEAR).toString())
        val eventDate : String = (nf.format(c.get(Calendar.DATE)).toString()+"-"+
                nf.format(monthValue).toString()+"-"+
                c.get(Calendar.YEAR).toString())
        if (SharedPreferencesClass.getString(requireContext(),"token")!=null)
        {
            convertToTimestamp(selectedDateTime, eventDate)
        }

        fragmentCalenderBinding.showAll.setOnClickListener {

            findNavController().navigate(R.id.action_calendarEventsFragment_to_allCalendarEventsFragment)

        }
        fragmentCalenderBinding.calendarView.setOnForwardPageChangeListener {
            monthValue = monthValue + 1
            eventsList = eventsViewModel.getMonthlyEvents(
                monthValue!!,
                fragmentCalenderBinding,
                requireContext(),
                view
            )
        }
        fragmentCalenderBinding.calendarView.setOnPreviousPageChangeListener {
            monthValue = monthValue-1
            eventsList = eventsViewModel.getMonthlyEvents(
                monthValue!!,
                fragmentCalenderBinding,
                requireContext(),
                view
            )
        }
        fragmentCalenderBinding.calendarView.setOnDayClickListener {

            //Number format class to display int value as 01
            val nf: NumberFormat = NumberFormat.getInstance()
            nf.setMinimumIntegerDigits(2)
            val selectedDateTime : String = (it.calendar.get(Calendar.MONTH)+1).toString()+"/"+
                    it.calendar.get(Calendar.DATE).toString()+"/"+
                    it.calendar.get(Calendar.YEAR).toString()
            var month : Int =it.calendar.get(Calendar.MONTH)+1
            val eventDate : String = nf.format( it.calendar.get(Calendar.DATE)).toString()+"-"+
                  nf.format( month).toString()+"-"+
                    it.calendar.get(Calendar.YEAR).toString()
            convertToTimestamp(selectedDateTime, eventDate)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
            {
                ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
            }
            Log.d(TAG, "setColor: //// color primary = "+ColorPrimary)
            fragmentCalenderBinding.calendarView.outlineAmbientShadowColor = (Color.parseColor(ColorPrimary).toInt())
            fragmentCalenderBinding.calendarView.outlineSpotShadowColor = (Color.parseColor(ColorPrimary).toInt())
            fragmentCalenderBinding.calendarView.outlineSpotShadowColor = (Color.parseColor(ColorPrimary).toInt())
        }
        fragmentCalenderBinding.calendarView.setHeaderColor(Color.parseColor(ColorPrimary).toInt())

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setEventsDisplay(arrayList: ArrayList<MonthlyCalendarEvents>, fragmentCalenderBinding: FragmentCalenderBinding) {

        val events: MutableList<EventDay> = ArrayList()

        val calendar = Calendar.getInstance()
        events.add(EventDay(calendar, null))

        for(i in arrayList!!)
        {
            Log.d(TAG, "onViewCreated: ///// i is ///'''" + i.eventsDate)
            var dt: LocalDateTime? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ToDateTime(i.eventsDate)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val calendar1 = Calendar.getInstance()
            calendar1.set(dt!!.year, (dt!!.monthValue-1), dt!!.dayOfMonth)
            events.add(EventDay(calendar1, shape))
        }
            val calendarView = fragmentCalenderBinding.calendarView as CalendarView

//            val min = Calendar.getInstance()
//            min.add(Calendar.MONTH, -2)
//
//            val max = Calendar.getInstance()
//            max.add(Calendar.MONTH, 2)
//
//            calendarView.setMinimumDate(min)
//            calendarView.setMaximumDate(max)

            calendarView.setEvents(events)
    }

    @RequiresApi(Build.VERSION_CODES.O)
   fun convertToTimestamp(selectedDateTime: String, eventDate: String) {
       val startDate = selectedDateTime+" 00:00:00"
       val endDate = (selectedDateTime+" 23:59:59")
        val startTS : Long =   (java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(startDate).getTime())

        val endTS : Long =   (java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(endDate).getTime())

       eventsViewModel.getEvents(startTS, endTS,fragmentCalenderBinding, eventDate)
   }

    fun setEventsAdapter(
        eventsList: ArrayList<CalendarEvents>,
        fragmentCalenderBinding: FragmentCalenderBinding)
    {
        Log.d(TAG, "setEventsAdapter:  events listr //" + eventsList)
        fragmentCalenderBinding.recyclerView.adapter = adapter
        adapter.submitList(eventsList)
    }

}
    @RequiresApi(Build.VERSION_CODES.O)
    fun ToDateTime(epochDate: Long): LocalDateTime? {
        val dt = Instant.ofEpochSecond(epochDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return dt
    }

