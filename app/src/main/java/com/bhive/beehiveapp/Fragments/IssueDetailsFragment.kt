package com.bhive.beehiveapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentReportanissueBinding

class IssueDetailsFragment : BaseFragment() {

    private lateinit var reportanissueBinding: FragmentReportanissueBinding
    var ColorPrimary : String ? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportanissueBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reportanissue,
            container,
            false
        )
        return reportanissueBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
    }

}