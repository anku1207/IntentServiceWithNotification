package com.example.intentservice.service

import android.R
import android.app.IntentService
import android.app.Notification
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.app.PendingIntent
import com.example.intentservice.MainActivity

class CustomServie : IntentService("CustomServie") {
    var CHANNEL_ID ="ForegroundServiceChannel"
    override fun onHandleIntent(p0: Intent?) {
        startForeground(1,getNotification())
        Thread.sleep(10000)  // wait for 1 second

        val intent =Intent("custom-event")
        intent.putExtra("data","hello")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }


    fun getNotification(): Notification? {
        //create channel id
        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Custom Foreground Service")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .setProgress(0,0,true)
            .build()
        return notification
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }


}