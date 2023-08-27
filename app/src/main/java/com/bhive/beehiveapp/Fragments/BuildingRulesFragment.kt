package com.bhive.beehiveapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.adapters.BuildingRulesAdapter
import com.bhive.beehiveapp.databinding.FragmentBuildingFacilitiesBinding
import com.bhive.beehiveapp.databinding.FragmentBuildingRulesBinding
import com.bhive.beehiveapp.models.BuildingRules
import com.bhive.beehiveapp.viewmodel.BuildingRulesViewModel

class BuildingRulesFragment : BaseFragment() {

    lateinit var fragmentBuildingRulesBinding: FragmentBuildingRulesBinding
    private lateinit var buildingRulesViewModel : BuildingRulesViewModel
    var ColorPrimary : String ? = null
    var adapter = BuildingRulesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBuildingRulesBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_building_rules, container , false)
        return fragmentBuildingRulesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildingRulesViewModel = ViewModelProvider(this).get(BuildingRulesViewModel::class.java)
        buildingRulesViewModel.getBUildingRules(
            SharedPreferencesClass.getString(requireContext(),
                "societyID").toString() ,
            fragmentBuildingRulesBinding
        )
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }

    }
    fun setAdapter( rulesList : ArrayList<BuildingRules>,   buildingRulesBinding: FragmentBuildingRulesBinding){
        buildingRulesBinding.rulesRecycler.adapter = adapter
        adapter.submitList(rulesList)
    }
}