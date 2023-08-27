package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.BuildingFacilitiesAdapter
import com.bhive.beehiveapp.databinding.FragmentBuildingFacilitiesBinding
import com.bhive.beehiveapp.models.BuildingFacilties
import com.bhive.beehiveapp.viewmodel.BuildingFacilitiesViewModel

class BuildingFacilitiesFragment : BaseFragment()
{
lateinit var buildingFacilitiesBinding: FragmentBuildingFacilitiesBinding
var ColorPrimary:String ? = null

private lateinit var buildingFacilitiesViewModel: BuildingFacilitiesViewModel
var adapter =  BuildingFacilitiesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildingFacilitiesBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_building_facilities , container, false)
        return buildingFacilitiesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildingFacilitiesViewModel = ViewModelProvider(this).get(BuildingFacilitiesViewModel::class.java)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
       buildingFacilitiesViewModel.getBUildingFacilities(SharedPreferencesClass.getString(requireContext() , "societyID")
            .toString(), buildingFacilitiesBinding)

    }

    fun setAdapter(
        facilities: ArrayList<BuildingFacilties>,
        buildingFacilitiesBinding: FragmentBuildingFacilitiesBinding
    ) {
        Log.d(TAG, "setAdapter: /// list here =  "+facilities)
        buildingFacilitiesBinding.rulesRecycler.adapter = adapter
        adapter.submitList(facilities)
    }
}