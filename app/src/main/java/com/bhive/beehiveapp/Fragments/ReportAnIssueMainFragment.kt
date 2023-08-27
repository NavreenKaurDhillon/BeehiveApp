package com.bhive.beehiveapp.Fragments

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentReportAnIssueMainBinding
import com.bhive.beehiveapp.interfaces.IssueStatusInterface
import com.bhive.beehiveapp.models.IssuesModelClass
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.viewmodel.ReportedIssuesViewModel

class ReportAnIssueMainFragment : BaseFragment() {

    lateinit var fragmentReportAnIssueMainBinding: FragmentReportAnIssueMainBinding
    private lateinit var reportedIssuesViewModel: ReportedIssuesViewModel
    var statusIssuesFragment =StatusIssuesFragment()
    private  var isseStatus : IssueStatusInterface? = null
    var arrayList = ArrayList<IssuesModelClass>()
    lateinit var dialog: Dialog
    var ColorPrimary : String ? = null

    companion object{
        var STATUS :Int= 5
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentReportAnIssueMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_report_an_issue_main,
            container,
            false
        )
        return fragmentReportAnIssueMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var DEFAULT_STATUS : Int? = null

        reportedIssuesViewModel = ViewModelProvider(this).get(ReportedIssuesViewModel::class.java)
        if (SharedPreferencesClass.getString(requireContext(),"primaryColor") != null)
        {
            ColorPrimary = SharedPreferencesClass.getString(requireContext(),"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        setColor()
        reportedIssuesViewModel.getIssues(fragmentReportAnIssueMainBinding, Constant.SOCIETY_ID)

        fragmentReportAnIssueMainBinding.line1.setBackgroundColor(resources.getColor(R.color.darkBlue))
        fragmentReportAnIssueMainBinding.line2.setBackgroundColor(Color.parseColor(ColorPrimary))
        fragmentReportAnIssueMainBinding.line3.setBackgroundColor(resources.getColor(R.color.darkBlue))
//        fragmentReportAnIssueMainBinding.line4.setBackgroundColor(resources.getColor(R.color.darkBlue))

        Log.d(TAG, "onViewCreated: /// ******************** def value = "+Constant.DEFAULT_SCREEN)
        Log.d(TAG, "onViewCreated: /// ******************** status  value = "+ STATUS)
        StatusIssuesFragment.LIMIT = Constant.OPEN_ISSUES
        STATUS = 1
        checkStatus(STATUS)

        StatusIssuesFragment.LIMIT = Constant.OPEN_ISSUES
        passStatus(STATUS!!)


        checkStatus(STATUS)
        fragmentReportAnIssueMainBinding.layout1.setOnClickListener {
            STATUS = 0
            checkStatus(STATUS)
        }
        fragmentReportAnIssueMainBinding.layout2.setOnClickListener {
            STATUS = 1
            checkStatus(STATUS)

        }
        fragmentReportAnIssueMainBinding.layout3.setOnClickListener {
            STATUS =2
            checkStatus(STATUS)

        }
//        fragmentReportAnIssueMainBinding.layout4.setOnClickListener {
//            STATUS =3
//            checkStatus(STATUS)
//        }


        fragmentReportAnIssueMainBinding.addIssue.setOnClickListener {
            Constant.issueId = 0
            findNavController().navigate(R.id.action_reportAnIssueMainFragment_to_reportAnIssueFragment)
            Log.d(TAG, "onViewCreated:/// add clicked here "+Constant.issueId)
        }
        Log.d(TAG, "onViewCreated: list is ////" + arrayList)

    }

    private fun setColor() {
        fragmentReportAnIssueMainBinding.addIssue.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(ColorPrimary)))
       fragmentReportAnIssueMainBinding.total.setTextColor(Color.parseColor(ColorPrimary))
        fragmentReportAnIssueMainBinding.open.setTextColor(Color.parseColor(ColorPrimary))
        fragmentReportAnIssueMainBinding.progress.setTextColor(Color.parseColor(ColorPrimary))
//        fragmentReportAnIssueMainBinding.line2.setBackgroundColor(Color.parseColor(ColorPrimary))
    }

    private fun checkStatus(status: Int) {
        when (STATUS){
            0 -> totalSelected()
            1 -> openSelected()
            2 -> myIssuesSelected()
            3 -> allLevelSelected()
            else -> openSelected()
        }
    }

    private fun totalSelected(){
        StatusIssuesFragment.LIMIT = Constant.TOTAL_ISSUES
        passStatus(STATUS!!)
        setSelected(fragmentReportAnIssueMainBinding.line1)
        setUnselected(fragmentReportAnIssueMainBinding.line2)
        setUnselected(fragmentReportAnIssueMainBinding.line3)
//        setUnselected(fragmentReportAnIssueMainBinding.line4)
    }
   private fun openSelected(){
        StatusIssuesFragment.LIMIT = Constant.OPEN_ISSUES
        passStatus(STATUS!!)
        setUnselected(fragmentReportAnIssueMainBinding.line1)
        setSelected(fragmentReportAnIssueMainBinding.line2)
        setUnselected(fragmentReportAnIssueMainBinding.line3)
//        setUnselected(fragmentReportAnIssueMainBinding.line4)
    }
    private fun myIssuesSelected(){
        StatusIssuesFragment.LIMIT = Constant.MY_ISSUES
        passStatus(STATUS!!)
        setUnselected(fragmentReportAnIssueMainBinding.line1)
        setUnselected(fragmentReportAnIssueMainBinding.line2)
        setSelected(fragmentReportAnIssueMainBinding.line3)
//        setUnselected(fragmentReportAnIssueMainBinding.line4)
    }

    fun allLevelSelected(){
        StatusIssuesFragment.LIMIT = Constant.ALL_LEVEL_ISSUES
        passStatus(STATUS!!)
        setUnselected(fragmentReportAnIssueMainBinding.line1)
        setUnselected(fragmentReportAnIssueMainBinding.line2)
        setUnselected(fragmentReportAnIssueMainBinding.line3)
//        setSelected(fragmentReportAnIssueMainBinding.line4)

    }
    private fun setUnselected(line: View) {
       line.setBackgroundColor(resources.getColor(R.color.darkBlue))
    }

    private fun setSelected(line: View) {
        line.setBackgroundColor(Color.parseColor(ColorPrimary))
    }

    private fun passStatus(status: Int ) {
        StatusIssuesFragment.status = status as Int
        fragmentManager?.beginTransaction()?.replace(R.id.issuesContainer, StatusIssuesFragment())
            ?.commit()
    }


}


