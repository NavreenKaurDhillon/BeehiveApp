package com.bhive.beehiveapp.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bhive.beehiveapp.MainActivity
import com.bhive.beehiveapp.repository.RetrofitRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

open class BaseFragment : Fragment(),RetrofitRepository{


    lateinit var baseActivity: MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity = activity as MainActivity
    }

     override fun onSuccess(message: String) {
         Log.d(TAG, "printMessage: /// message is = "+message)
//         Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_LONG).show()
         printMessage(message)
     }

     override fun onFailure(message: String) {
         printMessage(message)
     }


     override fun printMessage(message: String) {

     }

     fun isEmailValid(email: String?): Boolean {
         return Patterns.EMAIL_ADDRESS.matcher(email).matches()
     }

     fun isValidPassword(password : String): Boolean {
         val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
         val pattern = Pattern.compile(passwordPattern)
         val matcher = pattern.matcher(password)
         return matcher.matches()    }

    fun timeAgo(time: Long): String? {
        val currentTime = System.currentTimeMillis()
        if (time > currentTime || time <= 0) return null
        val time_elapsed = currentTime - time
        val thisDay: Int = SimpleDateFormat("dd", Locale.US).format(currentTime).toInt()
        val thisMonth: Int = SimpleDateFormat("MM", Locale.US).format(currentTime).toInt()
        val thisYear: Int = SimpleDateFormat("yyy", Locale.US).format(currentTime).toInt()
        val agoDay: Int = SimpleDateFormat("dd", Locale.US).format(time).toInt()
        val agoMonth: Int = SimpleDateFormat("MM", Locale.US).format(time).toInt()
        val agoYear: Int = SimpleDateFormat("yyy", Locale.US).format(time).toInt()
        val seconds = time_elapsed
        val minutes = Math.round((time_elapsed / 60000).toDouble()).toInt()
        val hours = Math.round((time_elapsed / 3600000).toDouble()).toInt()
        val days = Math.round((time_elapsed / 86400000).toDouble()).toInt()
        val weeks = Math.round((time_elapsed / 604800000).toDouble()).toInt()
        val months = Math.round((time_elapsed / 2600640000).toDouble()).toInt()
        val years = Math.round((time_elapsed / 31207680000).toDouble()).toInt()//Yesterday
       if ( (thisYear - agoYear) >= 1 ) {
       return SimpleDateFormat("MMM dd, yyyy - hh:ma", Locale.US).format(time)
       } else if ( (thisMonth - agoMonth) >= 1 ) {
       return SimpleDateFormat("MMM dd", Locale.US).format(time)
       } else if ( thisMonth == agoMonth && (thisDay - agoDay) == 1 ) {
       return "Yesterday"        } // Seconds
       else if ( seconds <= 60 ) {            return "just now"
       } //Minutes
       else if ( minutes <= 60 ) {
       return if (minutes == 1) {                "one minute ago"
       } else {                "$minutes minutes ago"
       }        } //Hours
         else if ( hours <= 24 ) {
       return if ( hours == 1 ) {   ""+( minutes - 60 ) + " minutes ago"
       } else {                "$hours hrs ago"            }
       } //Days
     else if ( days <= 7 ) {
           return if (days == 1)
    {                "$days day and " + (hours - 24) + " hrs ago"            }
       else {                "$days days ago"            }        } //Weeks
               else if
       ( weeks <= 4.3 ) {            return if (weeks == 1) {                "a week ago"            }
       else {                "$weeks weeks ago"            }
               }
        return null
       }
    }

