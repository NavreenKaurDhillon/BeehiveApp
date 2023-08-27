package com.bhive.beehiveapp.viewmodel

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bhive.beehiveapp.baseUrl.BaseUrlClass
import com.bhive.beehiveapp.databinding.FragmentForgotPasswordBinding
import com.bhive.beehiveapp.interfaces.ForgotPasswordInterface
import com.bhive.beehiveapp.models.DataModal
import com.bhive.beehiveapp.models.DeleteAccountResponse
import com.bhive.beehiveapp.networking.ApiInterface
import com.bhive.beehiveapp.repository.RetrofitRepository
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccountViewModel : ViewModel() {

    var responseInterface: RetrofitRepository? = null

    fun deleteUserAccount(context: Context){
        val retIn = BaseUrlClass.getInstance().create(ApiInterface::class.java)
        retIn.deleteAccount().enqueue( object : Callback<DeleteAccountResponse> {
            override fun onFailure(call: Call<DeleteAccountResponse>, t: Throwable) {
                responseInterface?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<DeleteAccountResponse>, response: Response<DeleteAccountResponse>) {
                if (response.code() == 200)
                {
                    Toast.makeText(context , "Account deleted successfully.",Toast.LENGTH_LONG).show()
                    responseInterface?.onSuccess(response.body()?.message.toString())

                }

            }
        })
    }
}

