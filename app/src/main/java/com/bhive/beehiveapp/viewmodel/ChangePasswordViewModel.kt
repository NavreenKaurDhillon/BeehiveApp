package com.bhive.beehiveapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bhive.beehiveapp.R
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.models.DataModal
import com.bhive.beehiveapp.models.changePasswordModelClass
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel()
    {
    var responseInterface: RetrofitRepository? = null
    fun change(jsonObject: JsonObject, requireContext: Context, view: View)
    {
        var oldPassword = jsonObject.get("old_password").asString
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.changePassword(jsonObject).enqueue( object : Callback<changePasswordModelClass>
        {
            override fun onFailure(call: Call<changePasswordModelClass>, t: Throwable)
            {
                responseInterface?.onFailure(t.message.toString())
                Toast.makeText(requireContext ,""+t.message.toString(),Toast.LENGTH_SHORT).show()
                navreeN@5
            }
            override fun onResponse(call: Call<changePasswordModelClass>, response: Response<changePasswordModelClass>)
            {
                if (response.code() == 200)
                {
                    Log.d(TAG, "onResponse: /// old p[ass is 200 "+oldPassword)
                    Log.d(TAG, "onResponse: /// change password 200"+response.message().toString())
                    Toast.makeText(requireContext ,"Password updated successfully.",Toast.LENGTH_LONG).show()
                    responseInterface?.onSuccess(response.body()?.message.toString())
                    Navigation.findNavController(view).navigate(R.id.action_changePasswordFragment_to_settingsFragment)
                }
                else
                {
                    Log.d(TAG, "onResponse: /// old p[ass is 400"+oldPassword)
                    Log.d(TAG, "onResponse: /// change password 400"+response.message())
                    Toast.makeText(requireContext ,"Old password is incorrect.",Toast.LENGTH_LONG).show()
                    responseInterface?.onSuccess(response.body()?.message.toString())
                }

            }
        })
    }

}
