package com.bhive.beehiveapp.baseUrl

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.bhive.beehiveapp.Fragments.BaseFragment
import com.bhive.beehiveapp.Fragments.SharedPreferencesClass
import com.bhive.beehiveapp.utils.constants.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseUrlClass  {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

   private val headerInterceptor : Interceptor = object : Interceptor{

       override fun intercept(chain: Interceptor.Chain): Response {
           var request: Request = chain.request()


           if(Constant.ACCESS_TOKEN == ""){
               Log.d(TAG, "intercept:  empty tokennnnnn and tokk = ")
           }
           else {

               request = request.newBuilder()
                   .addHeader("Authorization", "Bearer "+Constant.ACCESS_TOKEN)
                   .build()
           }
               val response: Response = chain.proceed(request)

               return response

       }
   }
    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(headerInterceptor).addInterceptor(interceptor)
    }.build()
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}