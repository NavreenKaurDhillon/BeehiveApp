package com.bhive.beehiveapp

import android.R.attr.id
import  android.R.attr.src
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bhive.beehiveapp.Fragments.ABC
import com.bhive.beehiveapp.Fragments.LoginFragment
import com.bhive.beehiveapp.Fragments.ReportAnIssueFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.adapters.DrawerItemsAdapter
import com.bhive.beehiveapp.databinding.ActivityMainBinding
import com.bhive.beehiveapp.databinding.DrawerHeaderLayoutBinding
import com.bhive.beehiveapp.interfaces.DrawerItemsInterface
import com.bhive.beehiveapp.models.DrawerItemsModelClass
import com.bhive.beehiveapp.models.UpdateTokenModelClass
import com.bhive.beehiveapp.utils.constants.Constant
import com.bhive.beehiveapp.utils.fonts.CustomTvRegular
import com.bhive.beehiveapp.utils.fonts.CustomTvSemiBold
import com.bhive.beehiveapp.viewmodel.AddIssueViewModel
import com.bhive.beehiveapp.viewmodel.GetProfileViewModel
import com.bhive.beehiveapp.viewmodel.NotificationsViewModel
import com.bhive.beehiveapp.viewmodel.UpdateFcmViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() , DrawerItemsInterface {
    lateinit var dialog: Dialog
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var drawerBinding: DrawerHeaderLayoutBinding
    private val adapter = DrawerItemsAdapter()
    var myFirebaseMessagingService =MyFirebaseMessagingService()
    lateinit var navController: NavController
    var pressedTime :  Long= 0
    var permCallback: PermissionCallback? = null
    lateinit var profileViewModel: GetProfileViewModel
    lateinit var addIssueViewModel: AddIssueViewModel
    lateinit var notificationsViewModel: NotificationsViewModel
//    lateinit var updateFcmViewModel: UpdateFcmViewModel
    var ColorPrimary : String ? = null

    companion object{
        var newColor : String ? = null
        var firebaseToken : String ? = null
        var count : String ? = null
        var LOGGED : Int ? = null
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        Constant.mainBinding = mainBinding

        profileViewModel = ViewModelProvider(this).get(GetProfileViewModel::class.java)
//        updateFcmViewModel = ViewModelProvider(this).get(UpdateFcmViewModel::class.java)
//        Constant.updateFcmViewModel = updateFcmViewModel
        setContentView(mainBinding.root)
        addIssueViewModel = ViewModelProvider(this).get(AddIssueViewModel::class.java)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        Constant.notificationsViewModel = notificationsViewModel
        Constant.addIssueViewModel = addIssueViewModel
        adapter.drawerItemsInterface = this
        var token = myFirebaseMessagingService.getToken(this)
        Log.d(TAG, "onCreate: ******** firebase token = "+token)
        Log.d(TAG, "onCreate: /// shraed pref = "+SharedPreferencesClass.getString(this , "token"))
        Log.d(TAG, "onCreate: /// const token = "+Constant.ACCESS_TOKEN)
        notificationsViewModel.getNotificationsCount()
        setBadges()
        Log.d(TAG, "onCreate: /// count = "+ count)


        firebaseToken = token

//        Log.d(TAG, "onCreate: /// shared pref color = "+SharedPreferencesClass.getString(this,"primaryColor"))
        if (SharedPreferencesClass.getString(this,"primaryColor")!=null)
        {
            ColorPrimary = SharedPreferencesClass.getString(this,"primaryColor").toString()
        }
        else
        {
            ColorPrimary = "#FEBD20"
        }
        Log.d(TAG, "onCreate: ///// color  = "+ColorPrimary)

        mainBinding.badgeBackground.setCardBackgroundColor(Color.parseColor(ColorPrimary))
        mainBinding.badgeBackground2.setCardBackgroundColor(Color.parseColor(ColorPrimary))
        mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

        setStatusBarColor(ColorPrimary!!)

        Log.d(TAG, "onCreate: ////main primary1 = "+ newColor)
        Log.d(TAG, "onCreate: ////constant primary1 = "+ Constant.PRIMARY_COLOR)

        mainBinding.notificationsIcon.setOnClickListener{
            mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))
            setLayout3(Color.parseColor(ColorPrimary))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            mainBinding.buildingHeading.text = "Notifications"
            navController.navigate(R.id.notificationsFragment)
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                "AppNotifications", "web_app",
                NotificationManager.IMPORTANCE_HIGH                                                                                                )
            var notificationManager : NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        Firebase.messaging.subscribeToTopic("general")
            .addOnCompleteListener { task ->
                var msg = "Successfull"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

        val nestedNavHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as? NavHostFragment
        navController = nestedNavHostFragment?.navController!!
        setNavController()
        Log.d(TAG, "onCreate: ////main primary 2= "+ newColor)
        Log.d(TAG, "onCreate: ////constant primary2 = "+ Constant.PRIMARY_COLOR)
        setUpBottomNav(mainBinding ,navController)

        val arrayList = ArrayList<DrawerItemsModelClass>()
        arrayList.add(DrawerItemsModelClass(R.drawable.ic_calendar2, "Building calender"))
        arrayList.add(DrawerItemsModelClass(R.drawable.ic_edit, "Issues list"))
        arrayList.add(DrawerItemsModelClass(R.drawable.alert, "Notifications"))
        arrayList.add(DrawerItemsModelClass(R.drawable.after_service, "After hours service"))
        arrayList.add(DrawerItemsModelClass(R.drawable.ic_gear, "Settings"))
        arrayList.add(DrawerItemsModelClass(R.drawable.office_block, "Facilities"))
        arrayList.add(DrawerItemsModelClass(R.drawable.icons8_rules_50, "Building rules"))
        arrayList.add(DrawerItemsModelClass(R.drawable.ic_logout2, "Logout"))

        //view binding
        val view = mainBinding.navigationView.getHeaderView(0)
        drawerBinding = DrawerHeaderLayoutBinding.bind(view)
        Log.d(TAG, "onCreate: ////main primary3 = "+ newColor)
        Log.d(TAG, "onCreate: ////constant primary3 = "+ Constant.PRIMARY_COLOR)
        drawerBinding.mainLl.setBackgroundColor(Color.parseColor(ColorPrimary))

        mainBinding.navigationView.setBackgroundColor(Color.parseColor(ColorPrimary))
        if (SharedPreferencesClass.getString(this,"token")!=null)
        {
            if (Constant.ACCESS_TOKEN.isNullOrEmpty() == true){
                Constant.ACCESS_TOKEN =SharedPreferencesClass.getString(applicationContext,"token")
                profileViewModel.userProfile()

                drawerBinding.buildingName.setText(SharedPreferencesClass.getString(applicationContext , "societyName"))
                Glide
                    .with(applicationContext)
                    .load(Constant.IMAGE_BASE_URL+ SharedPreferencesClass.getString(applicationContext , "societyLogo"))
                    .centerCrop()
                    .into(drawerBinding.header)
            }
            else{
                profileViewModel.userProfile()
                Log.d(TAG, "onCreate:  drawer //// name and logo = "+Constant.SOCIETY_LOGO +"/// ogo = "+Constant.SOCIETY_LOGO)
                drawerBinding.buildingName.setText(Constant.SOCIETY_NAME)
                Glide
                    .with(applicationContext)
                    .load(Constant.IMAGE_BASE_URL+ Constant.SOCIETY_LOGO)
                    .centerCrop()
                    .placeholder(R.drawable.ic_building)
                    .into(drawerBinding.header)
            }
        }


        drawerBinding.recycler.adapter = adapter
        adapter.submitList(arrayList)

        //get profile
//        drawerBinding.avatarImageView.setOnClickListener {
//
//            setNavController()
//            if ( mainBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
//            {
//                mainBinding.drawerLayout.closeDrawer(GravityCompat.START)
//            }
//            mainBinding.headingLayout.visibility = View.GONE
//            navController.navigate(R.id.profilePageFragment)
//
//        }


        if (navController != null) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment2 -> hideBothNav()
                    R.id.loginFragment2 -> hideBothNav()
                    R.id.registerFragment -> hideBothNav()
                    R.id.forgotPasswordFragment -> hideBothNav()
                    R.id.reportAnIssueFragment -> hideTopNav()
                    R.id.profilePageFragment -> hideBothNav()
                    R.id.changePasswordFragment -> hideTopNav()
                    R.id.allCalendarEventsFragment -> hideTopNav()

                    else -> {
                        showBothNav()
                        lockDrawer()
                    }
                }
            }

            if (navController != null) {

                mainBinding.navigationView.setupWithNavController(navController)
            } else {
                throw RuntimeException("Controller not found")
            }
        }
    }

    private fun setUpBottomNav(mainBinding: ActivityMainBinding, navController: NavController)
    {
        Log.d(TAG, "setUpBottomNav: // shread = "+SharedPreferencesClass.getString(this, "primaryColor"))
        Log.d(TAG, "setUpBottomNav: // main primary  = "+ newColor)
        Log.d(TAG, "setUpBottomNav: // constant primary  = "+Constant.PRIMARY_COLOR)
        if (ColorPrimary!=null){
            setLayout1(Color.parseColor(ColorPrimary))
        }
        else{
            setLayout1(Color.parseColor(SharedPreferencesClass.getString(this,"primaryColor")))
        }

        mainBinding.layout1.setOnClickListener {
            if (SharedPreferencesClass.getString(this,"primaryColor")?.length !=0)
            {
                ColorPrimary = SharedPreferencesClass.getString(this,"primaryColor").toString()
            }
            mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))
            mainBinding.progressBar.visibility = View.VISIBLE
//            notificationsViewModel.getNotificationsCount()
            setBadges()

            setLayout1(Color.parseColor(ColorPrimary))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            mainBinding.buildingHeading.text ="Building Calender"

            navController.navigate(R.id.calendarEventsFragment)

            Handler().postDelayed({
                mainBinding.progressBar.visibility = View.GONE

                 }, 1500)

        }
        mainBinding.layout2.setOnClickListener {
            mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))
            mainBinding.progressBar.visibility = View.VISIBLE
//            notificationsViewModel.getNotificationsCount()
            setBadges()
            if (SharedPreferencesClass.getString(this,"primaryColor")?.length !=0)
            {
                ColorPrimary = SharedPreferencesClass.getString(this,"primaryColor").toString()
            }
            setLayout2(Color.parseColor(ColorPrimary))
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            mainBinding.buildingHeading.text = " Report an Issue"

            navController.navigate(R.id.reportAnIssueFragment )

            Handler().postDelayed({
                mainBinding.progressBar.visibility = View.GONE

            }, 2000)

        }
        mainBinding.layout3.setOnClickListener {
            mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

//            notificationsViewModel.getNotificationsCount()
            setBadges()
            if (SharedPreferencesClass.getString(this,"primaryColor")?.length !=0)
            {
                ColorPrimary = SharedPreferencesClass.getString(this,"primaryColor").toString()
            }
            setLayout3(Color.parseColor(ColorPrimary))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            mainBinding.buildingHeading.text = "Notifications"

            navController.navigate(R.id.notificationsFragment)
            mainBinding.progressBar.visibility = View.VISIBLE
            Handler().postDelayed({
                mainBinding.progressBar.visibility = View.GONE
            }, 1500)

        }
    }

    private fun setLayout3(color: Int) {
        mainBinding.icon3.setColorFilter(color)
        mainBinding.text3.setTextColor(color)
    }

    private fun setLayout2(color: Int) {
        Constant.issueId = 0
        mainBinding.icon2.setColorFilter(color)
        mainBinding.text2.setTextColor(color)
        showBothNav()
    }

    private fun setLayout1(color: Int) {
        mainBinding.icon1.setColorFilter(color)
        mainBinding.text1.setTextColor(color)
    }

    fun hideTopNav() {
        mainBinding.headingLayout.visibility = View.GONE
    }

    fun hideBothNav(){
        mainBinding.bottomLayout.visibility = View.GONE
        mainBinding.headingLayout.visibility = View.GONE
    }

    fun showBothNav(){
        mainBinding.bottomLayout.visibility = View.VISIBLE
        mainBinding.headingLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed()
    {
        if (navController.currentDestination?.id ==R.id.calendarEventsFragment) {
            //            supportFragmentManager.popBackStack("FragmentsStack",0)
            if (pressedTime + 2000 > System.currentTimeMillis()){
                super.onBackPressed()
            }
            else
            {
                Toast.makeText(applicationContext,"Press back again to exit.",Toast.LENGTH_SHORT).show()
            }
        }
        else {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
                //            supportFragmentManager.popBackStack("FragmentsStack",0)
            }
            else{
                pressedTime= System.currentTimeMillis()
                navController.navigateUp()
                //            super.onBackPressed()
            }
        }
//        if (navigationController.currentDestination?.id == R.id.loginFragment2) {
//            if (pressedTime + 2000 > System.currentTimeMillis()) {
//                finish()
//            } else {
//                Toast.makeText(
//                    baseContext,
//                    "Press back again to exit",
//                    Toast.LENGTH_SHORT
//                ).show()
//                //super.onBackPressed()
//            }
//        } else {
//            super.onBackPressed()
//        }

        pressedTime  = System.currentTimeMillis()
    }

    fun setNavController()
    {
        this.mainBinding.drawerIcon.setOnClickListener {
            if (SharedPreferencesClass.getString(this , "primaryColor")!=null){
                Log.d(TAG, "setNavController: ///// shared = "+SharedPreferencesClass.getString(this, "primaryColor"))
                Log.d(TAG, "setNavController: ///// main primary  = "+ newColor)
                Log.d(TAG, "setNavController: ///// constant primaerry = "+Constant.PRIMARY_COLOR)
                ColorPrimary = SharedPreferencesClass.getString(this , "primaryColor")
                drawerBinding.buildingName.setText(SharedPreferencesClass.getString(applicationContext , "societyName"))
                Glide
                    .with(applicationContext)
                    .load(Constant.IMAGE_BASE_URL+ SharedPreferencesClass.getString(applicationContext , "societyLogo"))
                    .centerCrop()
                    .into(drawerBinding.header)
                drawerBinding.mainLl.setBackgroundColor(Color.parseColor(ColorPrimary))
                mainBinding.navigationView.setBackgroundColor(Color.parseColor(ColorPrimary))
            }
            mainBinding.drawerLayout.openDrawer(GravityCompat.START)
//            drawerProfileViewModel.userProfile(drawerBinding)
        }
    }
    fun lockDrawer()
    {
        mainBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START)

    }

    override fun openFragment(pos: Int, view: View) {
        Log.d(ContentValues.TAG, "openFragment: " + pos)
        val heading = mainBinding.buildingHeading
        val notifications = mainBinding.notificationsIcon

        when (pos)
        {
            0 -> {
                setLayout1(Color.parseColor(ColorPrimary))
                setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
                heading.text = "Building Calender"
                notifications.visibility = View.VISIBLE
//                mainBinding.progressBar.visibility = View.VISIBLE
                navController.navigate(R.id.buildingCalendarFragment)
//                Constant.notificationsViewModel?.getNotificationsCount()
//                setBadges()
//                Handler().postDelayed({
//                  mainBinding.progressBar.visibility  = View.GONE
//                }, 2000)
            }
            1 -> {
                mainBinding.drawerLayout.close()
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                heading.text = "Issues List"
                notifications.visibility = View.GONE
                setLayout2(Color.parseColor(ColorPrimary))
                setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
//                mainBinding.progressBar.visibility = View.VISIBLE
                navController.navigate(R.id.reportAnIssueMainFragment)
                Handler().postDelayed({
//                    mainBinding.progressBar.visibility=View.GONE

                }, 2000)

            }
            2 -> {
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
            heading.text = "Notifications"
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(Color.parseColor(ColorPrimary))
            mainBinding.progressBar.visibility = View.VISIBLE
                navController.navigate(R.id.notificationsFragment)
                Handler().postDelayed({
                    mainBinding.progressBar.visibility=View.GONE
                    notifications.visibility = View.GONE
                }, 2000)
        }
            3 -> {
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
                heading.text = "After Hours Service"
                setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
                notifications.visibility = View.VISIBLE
                navController.navigate(R.id.afterHourServiceFragment)
                Handler().postDelayed({
                    mainBinding.progressBar.visibility = View.GONE
                }, 2000)

            }
            4 -> {
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
            heading.text = "Settings"
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            notifications.visibility = View.VISIBLE
                navController.navigate(R.id.settingsFragment)
                Handler().postDelayed({
                   mainBinding.progressBar.visibility =View.GONE
                }, 2000)

        }
            5 -> {
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
            heading.text = "Facilities"
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
mainBinding.progressBar.visibility = View.VISIBLE
                navController.navigate(R.id.buildingFacilitiesFragment)
                Handler().postDelayed({
                    mainBinding.progressBar.visibility = View.GONE
                }, 1500)

        }
            6 -> {
                mainBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(Color.parseColor(ColorPrimary))

                mainBinding.drawerLayout.close()
            heading.text = "Building Rules"
            setLayout1(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout2(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            setLayout3(resources.getColor(com.mikelau.croperino.R.color.dark_gray))
            notifications.visibility = View.VISIBLE
                mainBinding.progressBar.visibility =View.VISIBLE
                navController.navigate(R.id.buildingRulesFragment)
                Handler().postDelayed({
                    mainBinding.progressBar.visibility = View.GONE
                }, 1500)

        }
            7 -> {
                heading.visibility = View.VISIBLE
                mainBinding.mainFragment.setBackgroundColor(resources.getColor(R.color.white))
                notifications.visibility = View.GONE
                //onClickLogout()
                mainBinding.drawerLayout.close()
                showAlertDialogLeave()
            }
//            2 -> {
//                mainBinding.drawerLayout.close()
//                heading.text = "Marketplace"
//                notifications.visibility = View.GONE
//                navController.navigate(R.id.marketplaceFragment)
//            }

        }
    }

    private fun showAlertDialogLeave() {

        dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog_custom_view)
        Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window?.attributes?.windowAnimations = R.style.dialogAnimation2
//        dialog.getWindow()?.setFlags(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
        val dm = DisplayMetrics()
        this@MainActivity.windowManager.defaultDisplay.getMetrics(dm)
        val width = (dm.widthPixels * 0.9).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()

        val titleTV = dialog.findViewById<CustomTvSemiBold>(R.id.titleTV)
        titleTV.setTextColor(Color.parseColor(ColorPrimary))
        val leaveTV = dialog.findViewById<CustomTvRegular>(R.id.leaveTV)
        leaveTV.setTextColor(Color.parseColor(ColorPrimary))
        val cancelTV = dialog.findViewById<CustomTvRegular>(R.id.cancelTV)
        leaveTV.setOnClickListener {
            dialog.dismiss()
            mainBinding.buildingHeading.visibility = View.VISIBLE
            LOGGED = 1
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Constant.ACCESS_TOKEN = null
            SharedPreferencesClass.cleanPref(applicationContext)
            Handler().postDelayed({

                Toast.makeText(applicationContext , "Logged out successfully.", Toast.LENGTH_SHORT).show()
            }, 1500)
        }
        cancelTV.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun onLogout(abc: ABC) {

    }
    fun checkSelfPermission(perms: Array<String>, permCallback: PermissionCallback) {
        this.permCallback = permCallback
        ActivityCompat.requestPermissions(this, perms, 99)
    }

    interface PermissionCallback {
        fun permGranted()
        fun permDenied()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permGrantedBool = false
        when (requestCode) {
            99 -> {
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(
                            this, getString(R.string.not_sufficient_permissions)
                                    + getString(R.string.app_name)
                                    + getString(R.string.permissions), Toast.LENGTH_SHORT
                        ).show()
                        permGrantedBool = false
                        break
                    } else {
                        permGrantedBool = true
                    }
                }
                if (permCallback != null) {
                    if (permGrantedBool)
                        permCallback!!.permGranted()
                    else
                        permCallback!!.permDenied()
                }
            }
        }
    }


    open fun saveBitmapToFile(file: File): File? {
        return try {

            Log.d(TAG, "saveBitmapToFile: // file received is "+file)
            ReportAnIssueFragment.bitmapFile = file
            // BitmapFactory options to downsize the image
            val o: BitmapFactory.Options = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            Log.d(TAG, "saveBitmapToFile: // input stream is "+inputStream)
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2: BitmapFactory.Options = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap: Bitmap? = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun setStatusBarColor(primaryColor: String?) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.parseColor(this.ColorPrimary)
            } 
        }
        catch (E : java.lang.Exception)
        {
            Log.d(TAG, "setStatusBarColor: /// window is null")
        }
       
    }

    fun setBadges() {

        val countN = Constant.notificationsViewModel?.getNotificationsCount()
        Log.d(TAG, "setBadges: count = ///////////"+ countN)
        Log.d(TAG, "setBadges: count = ///////////"+ count)
        //top count
//        Constant.mainBinding?.badgeBackground2?.setBackgroundColor(Color.parseColor(ColorPrimary))

        if (countN!=0 && countN!=null)
        {
            Constant.mainBinding?.badgeText?.setText(countN.toString())
            Constant.mainBinding?.badgeBackground?.visibility = View.VISIBLE
            Constant.mainBinding?.badgeText2?.setText(countN.toString())
            Constant.mainBinding?.badgeBackground2?.visibility  = View.VISIBLE

            //bottom count

//        Constant.mainBinding?.badgeBackground?.setBackgroundColor(Color.parseColor(ColorPrimary))
            Constant.mainBinding?.badgeBackground?.visibility  = View.VISIBLE
            Constant.mainBinding?.badgeBackground2?.visibility  = View.VISIBLE
//            Constant.mainBinding?.badgeBackground?.setBackgroundColor(Color.parseColor(ColorPrimary))
//            Constant.mainBinding?.badgeBackground2?.setBackgroundColor(Color.parseColor(ColorPrimary))

        }
        else{
            Constant.mainBinding?.badgeBackground?.visibility  = View.GONE
            Constant.mainBinding?.badgeBackground2?.visibility  = View.GONE
        }
    }

}











