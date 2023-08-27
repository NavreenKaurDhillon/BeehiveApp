package com.bhive.beehiveapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.bhive.beehiveapp.utils.constants.Constant
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessaging.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.JsonObject
import me.leolin.shortcutbadger.ShortcutBadger

class MyFirebaseMessagingService : FirebaseMessagingService() {

    var jsonObject = JsonObject()
    //code 2
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
        if (p0.notification != null) {
            Log.d("notifications",p0.notification.toString())
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            showNotification(
                p0.notification?.title,
                p0.notification?.body
            )
        }
        super.onMessageReceived(p0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(
        title: String?,
        message: String?
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        // Assign channel ID
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Pass the intent to PendingIntent to start the
        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        var builder: Notification.Builder = Notification.Builder(
            applicationContext,
            "AppNotifications"
        )
            .setSmallIcon(R.drawable.android)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.beehive))
            .setColor(resources.getColor(R.color.colorPrimary))
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentText(message)

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
//        builder = if (Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.JELLY_BEAN
//        ) {
////            builder.setContent(
////              //  getCustomDesign(title, message)
////            )
//        } // If Android Version is lower than Jelly Beans,
//        else {
//
//        }

        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Installations", "Installation ID: " + task.result)
            } else {
                Log.e("Installations", "Unable to get Installation ID")
            }
        }
        FirebaseInstallations.getInstance().getToken(/* forceRefresh */ true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Installations", "Installation auth token: " + task.result?.token)
                } else {
                    Log.e("Installations", "Unable to get Installation auth token")
                }
            }

        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Installations", "Installation ID: " + task.result)
            } else {
                Log.e("Installations", "Unable to get Installation ID")
            }
        }

        FirebaseInstallations.getInstance().delete().addOnCompleteListener { task ->
            if (task.isComplete) {
                Log.d("Installations", "Installation deleted")
            }  else {
                Log.e("Installations", "Unable to delete Installation")
            }
        }

        builder.setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.android)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.beehive))
            .setColor(resources.getColor(R.color.colorPrimary))
            .setChannelId("AppNotifications")
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                "AppNotifications", "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        var b = builder.build()
        ShortcutBadger.applyNotification(applicationContext, b, 3)
        notificationManager.notify(0, builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
         val refreshedToken  =  getInstance().token.toString()
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb",token).apply();
        updateToken(refreshedToken)
        Log.d(TAG, "onNewToken: //// token = "+refreshedToken)
    }

    private fun updateToken(refreshedToken: String) {
        Log.d(TAG, "updateToken: //// updated token = "+refreshedToken)
        jsonObject.addProperty("device_token" , refreshedToken)
        Constant.updateFcmViewModel?.updateFcmToken(jsonObject)
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
    }


}


