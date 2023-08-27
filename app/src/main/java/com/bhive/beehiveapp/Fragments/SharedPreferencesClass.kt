package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.bhive.beehiveapp.utils.constants.Constant

class SharedPreferencesClass {
    companion object{

        fun cleanPref(context: Context) {
            val settings = context.getSharedPreferences(Constant.SP_Name, Context.MODE_PRIVATE)
            settings.edit().clear().apply()
        }

        fun saveString(context: Context,key: String?, value: String?) {
            Log.d(TAG, "saveString: "+value)
            val token = context.getSharedPreferences(Constant.SP_Name, Context.MODE_PRIVATE)
            val editor = token.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getString(context: Context,key: String?): String? {
            return getStrings(context, key, null)
        }

        fun getStrings(context : Context, key: String?, defaultVal: String?): String? {
            val token = context.getSharedPreferences(Constant.SP_Name, Context.MODE_PRIVATE)
            return token.getString(key, defaultVal)
        }

//        fun getData(context: Context,email: String, password: String): Int {
//            var flag = 0
//            SharedPreferencesClass = context.getSharedPreferences("SPFile", MODE_PRIVATE)
//            val s_email: String = SharedPreferencesClass.getString("email", "")
//            val s_password: String = SharedPreferencesClass.getString("password", "")
//            flag = if (email == s_email && password == s_password) {
//                1
//            } else {
//                0
//            }
//            return flag
//        }

    }

}