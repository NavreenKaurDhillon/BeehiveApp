package com.bhive.beehiveapp.viewmodel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.Fragments.RegisterFragment
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

class RegisterationViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null
    var flag : Int? = null
    private lateinit var access_token : String
    var mainActivity = MainActivity()


    fun register(jsonObject: JsonObject, view: View,context: Context){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        RegisterFragment.DATA_LOADED=1

        retIn.registerUser(jsonObject).enqueue( object : Callback<DataModal> {
            override fun onFailure(call: Call<DataModal>, t: Throwable) {
                flag = 0
                responseInterface?.onFailure(t.message.toString())


            }
            override fun onResponse(call: Call<DataModal>, response: Response<DataModal>) {
                if (response.code() == 200) {

                    RegisterFragment.REGISTERED_SUCCESS = 1
//                    RegisterFragment.isRegistered = 1
                    flag = 1
                    responseInterface?.onSuccess(response.body()?.message.toString())
                    val constant : Constant.Companion?=null
                    constant?.getAcess(response.body()?.response?.accessToken.toString())

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
                    Constant.ACCESS_TOKEN = response.body()?.response?.accessToken
                    MainActivity.newColor = response.body()?.response?.primaryColor
                    mainActivity.ColorPrimary = response.body()?.response?.primaryColor
                    mainActivity.setStatusBarColor(response.body()?.response?.primaryColor)
                    Constant.mainBinding?.badgeBackground?.setBackgroundColor(
                        response.body()?.response?.primaryColor!!.toColorInt()
                    )
                    Constant.mainBinding?.badgeBackground2?.setBackgroundColor(
                        response.body()?.response?.primaryColor!!.toColorInt()
                    )

                    response.body()?.response?.primaryColor?.let {
                        Constant.mainBinding?.icon1?.setColorFilter(
                            it.toColorInt())
                    }
                    response.body()?.response?.primaryColor?.let {
                        Constant.mainBinding?.text1?.setTextColor(
                            it.toColorInt())
                    }
                    ReportAnIssueFragment.FLOOR_NO = Constant.FLOOR_NO

                    var tokk = SharedPreferencesClass.getString(context,"token")


                    Toast.makeText(context , response.body()?. message.toString() ,Toast.LENGTH_LONG).show()
                    val action = R.id.action_registerFragment_to_calendarEventsFragment
                    Navigation.findNavController(view).navigate(action)
                }
                if (response.code() == 400) {
                    Toast.makeText(context , "Email or Phone is already registered. ",Toast.LENGTH_LONG).show()
                    responseInterface?.onSuccess("Email or Phone is already registered")

                }

            }
        })
    }

}

