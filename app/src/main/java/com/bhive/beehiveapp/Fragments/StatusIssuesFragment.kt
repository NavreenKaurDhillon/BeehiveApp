package com.bhive.beehiveapp.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentIssuesBinding
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.GetIssuesViewModel
import com.bhive.beehiveapp.viewmodel.TotalIssuesViewModel

class StatusIssuesFragment : Fragment() {
    companion object{
        var dataFetched2: Int = 0
        var status : Int = 0
        var LIMIT: Int?= 0
        var dataFetched : Int = 0
        var ColorPrimary : String ? = null

    }
    var PAGE :Int = 1
    private lateinit var fragmentIssuesBinding: FragmentIssuesBinding
    private lateinit var getTotalIssuesViewModel: TotalIssuesViewModel
    private lateinit var getIssuesViewModel: GetIssuesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        fragmentIssuesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_issues, container, false)
        return fragmentIssuesBinding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTotalIssuesViewModel = ViewModelProvider(this).get(TotalIssuesViewModel::class.java)
        getIssuesViewModel = ViewModelProvider(this).get(GetIssuesViewModel::class.java)

        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        fragmentIssuesBinding.progressBar.indeterminateTintList = (ColorStateList.valueOf(android.graphics.Color.parseColor(ColorPrimary)))
        fragmentIssuesBinding.progressBarBottom.indeterminateTintList = (ColorStateList.valueOf(android.graphics.Color.parseColor(ColorPrimary)))

//        getIssuesViewModel.getIssuesWithStatus(status, fragmentIssuesBinding)
        Log.d(TAG, "getStatus: //sta" + status)
        val societyId : String =SharedPreferencesClass.getString(requireContext(),"societyID").toString()
        fragmentIssuesBinding.progressBar.visibility = View.VISIBLE
        fragmentIssuesBinding.progressBarBottom.visibility = View.GONE

        if (LIMIT ==0){
            LIMIT = status
            fragmentIssuesBinding.progressBar.visibility = View.GONE

        }
        Log.d(TAG, "onViewCreated: ///// limit is "+LIMIT)

        getData(fragmentIssuesBinding , getIssuesViewModel, getTotalIssuesViewModel, PAGE,LIMIT)

        Handler().postDelayed({

            if (dataFetched == 1){
                fragmentIssuesBinding.progressBar.visibility = View.GONE
                dataFetched = 0
            }

            }, 2000)

        fragmentIssuesBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            //
             fragmentIssuesBinding.progressBarBottom.visibility = View.VISIBLE

                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    this.PAGE++
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    if (this.PAGE > LIMIT!!) {
                        //do Nothing
                        Handler().postDelayed({

                            if (dataFetched2 == 1){
                                fragmentIssuesBinding.progressBarBottom.visibility = View.GONE
                                dataFetched2 = 0
                                Toast.makeText(requireContext(),"All issues fetched successfully.",Toast.LENGTH_LONG).show()
                            }
                        }, 2000)
//                            fragmentIssuesBinding.progressBarBottom.visibility = View.INVISIBLE

//                        Handler().postDelayed({
//                            getData(
//                                fragmentIssuesBinding,
//                                getIssuesViewModel,
//                                getTotalIssuesViewModel,
//                                PAGE-1,
//                                LIMIT
//                            )
//                            fragmentIssuesBinding.progressBarBottom.visibility = View.INVISIBLE
//
//                        }, 4000)
                    }
                    else{
                        getData(
                            fragmentIssuesBinding,
                            getIssuesViewModel,
                            getTotalIssuesViewModel,
                            PAGE,
                            LIMIT
                        )
                        Handler().postDelayed({

                            if (dataFetched2 == 1){
                                fragmentIssuesBinding.progressBarBottom.visibility = View.GONE
                                dataFetched = 0
                            }

                        }, 2000)
                    }

                }

        })
    }

    private fun getData(
        fragmentIssuesBinding: FragmentIssuesBinding,
        getIssuesViewModel: GetIssuesViewModel,
        getTotalIssuesViewModel: TotalIssuesViewModel,
        PAGE: Int,
        LIMIT: Int?
    ) {
        if (this.PAGE > LIMIT!! && LIMIT!=0) {
            //do Nothing
            Toast.makeText(requireContext(),"All issues fetched successfully.",Toast.LENGTH_LONG).show()
            return
        }
        else{
            Log.d(TAG, "getData: ////else page is "+PAGE)
            Log.d(TAG, "getData: ////else limit is "+LIMIT)
            Log.d(TAG, "getData: /// society id is "+ Constant.SOCIETY_ID)
            Log.d(TAG, "getData: /// society id saved is "+ SharedPreferencesClass.getString(requireContext(),"societyID"))

            val societyId = SharedPreferencesClass.getString(requireContext(),"societyID")
            if(status ==0 )
            {
                //total issues
                //owner type =2
                this.getTotalIssuesViewModel.getTotalIssues(societyId.toString(),
                    this.fragmentIssuesBinding,
                    this.PAGE)
            }
            else if (status==1){
                //open issues
                //owner type =2
                //status = 1
                this.getIssuesViewModel.getIssuesWithStatus(status,
                    societyId.toString() ,
                    this.fragmentIssuesBinding,
                    this.PAGE
                )
            }
            else
            {
                //by me issues
                //owner type =2
                this.getIssuesViewModel.getIssuesWithOwner((status-1),
                   societyId.toString() ,this.fragmentIssuesBinding,
                    this.PAGE
                )
            }
        }
    }
}