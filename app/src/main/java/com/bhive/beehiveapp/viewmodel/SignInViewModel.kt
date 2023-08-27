package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import com.bhive.beehiveapp.Fragments.ABC
import com.bhive.beehiveapp.Fragments.ReportAnIssueFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.DataModal
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel  : ViewModel() {

    var responseInterface: RetrofitRepository? = null

    var abc : ABC?= null
    var flag : Int = 0
    var mainActivity = MainActivity()

    fun signIn(jsonObject: JsonObject,view : View,context :Context): Int
    {
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.signInUser(jsonObject).enqueue( object : Callback<DataModal>
        {
            override fun onFailure(call: Call<DataModal>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
            }
            override fun onResponse(call: Call<DataModal>, response: Response<DataModal>)
            {
                val constant : Constant.Companion?=null

                if (response.code() == 200)
                {
                    Constant.USER_MAIN_BUILDING_LOGO = response.body()?.response?.logo.toString()
                    flag = 1
//                    responseInterface?.onSuccess(response.body()?.message.toString())
                    constant?.getAcess(response.body()?.response?.accessToken.toString())
                    Log.d(TAG, "onResponse:  token"+responseInterface?.onSuccess(response.body()?.response?.accessToken.toString()))
                    Log.d(TAG, "onResponse: view model "+ response.body()?.response?.accessToken.toString())

                    SharedPreferencesClass.saveString(context,"token", response.body()?.response?.accessToken.toString())
                    SharedPreferencesClass.saveString(context,"societyID", response.body()?.response?.societyId.toString())
                    SharedPreferencesClass.saveString(context,"userID", response.body()?.response?.id.toString())
                    SharedPreferencesClass.saveString(context,"floorNo", response.body()?.response?.floorNumber.toString())
                    SharedPreferencesClass.saveString(context,"societyLogo", response.body()?.response?.logo.toString())
                    SharedPreferencesClass.saveString(context,"societyName", response.body()?.response?.society.toString())
                    SharedPreferencesClass.saveString(context,"lastName", response.body()?.response?.lastName.toString())
                    SharedPreferencesClass.saveString(context,"firstName", response.body()?.response?.firstName.toString())
                    SharedPreferencesClass.saveString(context,"secondaryColor", response.body()?.response?.secondaryColor.toString())
                    SharedPreferencesClass.saveString(context,"primaryColor", response.body()?.response?.primaryColor.toString())
                    Constant.PRIMARY_COLOR = response.body()?.response?.primaryColor
                    Constant.SOCIETY_ID = response.body()?.response?.societyId.toString()
                    Constant.SOCIETY_NAME = response.body()?.response?.society.toString()
                    Constant.SOCIETY_LOGO = response.body()?.response?.logo.toString()
                    Constant.SOCIETY_ID = response.body()?.response?.societyId.toString()
                    Constant.USER_ID = response.body()?.response?.id
                    Constant.FLOOR_NO = response.body()?.response?.floorNumber
                    Constant.NAME = response.body()?.response?.firstName+ response.body()?.response?.lastName

                    ReportAnIssueFragment.FLOOR_NO = Constant.FLOOR_NO

                    var tokk = SharedPreferencesClass.getString(context,"token")

                    Log.d(TAG, "onResponse: shared pref ???????"+tokk)

                    Log.d(TAG, "onResponse: beforeee "+Constant.ACCESS_TOKEN)
                    Constant.ACCESS_TOKEN = response.body()?.response?.accessToken.toString()
                    Log.d(TAG, "onResponse: afterr "+Constant.ACCESS_TOKEN)
                    MainActivity.newColor = response.body()?.response?.primaryColor
                    mainActivity.ColorPrimary = response.body()?.response?.primaryColor
                    mainActivity.setStatusBarColor(response.body()?.response?.primaryColor)

                    mainActivity.setBadges()

                    response.body()?.response?.primaryColor?.let {
                        Constant.mainBinding?.icon1?.setColorFilter(
                            it.toColorInt())
                    }
                    response.body()?.response?.primaryColor?.let {
                        Constant.mainBinding?.text1?.setTextColor(
                            it.toColorInt())
                    }
                    findNavController(view).navigate(R.id.buildingCalendarFragment)
                    Handler().postDelayed(
                        {
//                        Toast.makeText(context , response.body()?.message.toString(),Toast.LENGTH_LONG).show()

                    }, 2000)
                }
                if (response.code() == 400)
                {
                    Toast.makeText(context , "Invalid credentials",Toast.LENGTH_LONG).show()
                }

            }
        })
        Log.d(TAG, "signIn: //// flag ="+flag)
        return flag
    }

}

