package com.jastley.notificationintent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_FIRST = 1
    private val REQUEST_CODE_OTHER = 2

    private val channelName = "TestChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
    }

    fun onClickNotificationTypeOne(view: View) {
        createNotification(112, REQUEST_CODE_FIRST, "The notification title")
    }


    fun onClickNotificationTypeTwo(view: View) {
        createNotification(72, REQUEST_CODE_OTHER, "Another different title")
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val descriptionText = getString(R.string.channel_description)
            val channel = NotificationChannel(channelName, name, importance).apply {
                description = descriptionText
            }
            //Register channel
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification (id: Int, code: Int, title : String) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK //or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("notificationType", code)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            REQUEST_CODE_FIRST,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, channelName)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText("Here is the content text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setChannelId(channelName)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(id, builder.build())
        }
    }

}
