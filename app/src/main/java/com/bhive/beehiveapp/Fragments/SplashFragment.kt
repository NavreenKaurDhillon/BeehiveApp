package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.databinding.FragmentSplashScreenBinding
import com.bhive.beehiveapp.utils.constants.Constant

class SplashFragment : BaseFragment(){
    private lateinit var splashScreenBinding: FragmentSplashScreenBinding
    var flag =0
    var ColorPrimary : String ? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        splashScreenBinding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_splash_screen, container, false)
        return splashScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (SharedPreferencesClass.getString(requireContext(),"token")!=null)
        {
            Log.d(TAG, "onViewCreated: //// loggedib ")
        }
        flag =  isUserLogged(requireContext())


//        splashScreenBinding.progressBar.visibility = View. VISIBLE

        Handler().postDelayed({

            if(flag == 0) {

                findNavController().navigate(R.id.action_splashFragment2_to_loginFragment2)
            }
            else{
                findNavController().navigate(R.id.action_splashFragment2_to_reportAnIssueMainFragment)
            } }, 2000)

    }

     fun isUserLogged(context: Context): Int {
        var flag = 0
        Constant.USER_ID = SharedPreferencesClass.getString(context,"userID")?.toInt()
        Constant.ACCESS_TOKEN = SharedPreferencesClass.getString(context,"token")
        Constant.SOCIETY_ID = SharedPreferencesClass.getString(context,"societyID")
        Constant.FLOOR_NO = SharedPreferencesClass.getString(context,"floorNo")
        if(SharedPreferencesClass.getString(context,"token") != null ){
            flag = 1

        }
        return flag
    }
}

