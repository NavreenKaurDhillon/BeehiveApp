package com.bhive.beehiveapp.utils.constants

import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.viewmodel.AddIssueViewModel
import com.bhive.beehiveapp.Fragments.NotificationsFragment
import com.bhive.beehiveapp.databinding.ActivityMainBinding
import com.bhive.beehiveapp.databinding.FragmentNotificationsBinding
import com.bhive.beehiveapp.databinding.FragmentReportanissueBinding
import com.bhive.beehiveapp.models.Notifications
import com.bhive.beehiveapp.viewmodel.NotificationsViewModel
import com.bhive.beehiveapp.viewmodel.UpdateFcmViewModel
import com.bhive.beehiveapp.viewmodel.UpdateIssueStatusViewModel
import java.io.File

class Constant {
    companion object{

        val notificationsFragment1: NotificationsFragment = NotificationsFragment()
        var updateFcmViewModel: UpdateFcmViewModel? = null
        var reportAnIssueBinding: FragmentReportanissueBinding?= null
        var updateIssueViewModel : UpdateIssueStatusViewModel ? = null
         var notificationsViewModel : NotificationsViewModel ? = null
        var SOCIETY_NAME: String? = null
         var SOCIETY_LOGO: String? = null
        var PRIMARY_COLOR: String? =  null
        var USER_MAIN_BUILDING_LOGO : String?= null
         var SOCIETY_ID: String?=null
        val ENCRYPT_KEY: String?="Qhl123"
        var building_id: Int? = 0
        val SP_Name: String? = "SPFile"
        var selectedBuilding : Int? = 0
        var USER_ID : Int?= null
        var DEFAULT_SCREEN : Int?= null
        var FLOOR_NO : String?= null
        var BUILDING_NAME : String ? = null
        var BUILDING_LOGO : String ? = null
        var FIRST_NAME : String ? = null
        var LAST_NAME : String ? = null
        var PHONE : String ? = null
        var EMAIL : String ? = null
        val CHANNEL_ID : String= "notification_key"
        val CHANNEL_NAME : String= "beehive_app_chanel"
        //base url staging server
//        const val BASE_URL : String = "http://staging.qualhon.net:3088/api/v1/"
//        const val IMAGE_BASE_URL : String = "http://staging.qualhon.net:3088/"

            // urls for production server
             const val BASE_URL : String = "http://3.24.166.169:3000/api/v1/"
        const val IMAGE_BASE_URL : String = "http://3.24.166.169:3000/"

        var addIssueViewModel : AddIssueViewModel? = null
        var imagesData : ArrayList<File> = arrayListOf()
        var finalImagesData : ArrayList<File> = arrayListOf()
        var NAME : String ? = null
        var mainActivity = MainActivity()
        var ACCESS_TOKEN : String? = ""
        var RESET_TOKEN : String? = ""
        var issueId : Int? = 0
        var TOTAL_ISSUES : Int? = 0
        var OPEN_ISSUES : Int? = 0
        var MY_ISSUES : Int? = 0
        var ALL_LEVEL_ISSUES : Int? = 0
        var description : String? = ""
        var notificationsBinding : FragmentNotificationsBinding?=null
        var updatedNotificationsList : ArrayList<Notifications>? = null
        var mainBinding : ActivityMainBinding ? = null

        fun getAcess(access_token : String){
            this.ACCESS_TOKEN=access_token
        }
    }
}