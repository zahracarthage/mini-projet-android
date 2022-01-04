package com.example.mini_projet.views

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.mini_projet.R
import com.example.mini_projet.views.Accueil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.mini_projet"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        if(remoteMessage.getNotification() != null)
        {
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }

    }


    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String,message: String): RemoteViews{
        val remoteView = RemoteViews("com.example.mini_projet",R.layout.notification)
        remoteView.setTextViewText(R.id.titlenotif,title)
        remoteView.setTextViewText(R.id.messagenotif,message)
        remoteView.setImageViewResource(R.id.notif_logo,R.drawable.yum)

        return remoteView
    }

    fun generateNotification(title : String, message : String)
    {
        val intent = Intent(this,Accueil::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        // channel id, channel name
            var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.yum)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000,1000,1000,1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notifcationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notifcationChannel)
        }

        notificationManager.notify(0,builder.build())

    }

}