package com.example.fz.lifecycleapp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private val tag = "MyService"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, "service_channel")
            .setContentTitle("My service")
            .setContentText("Сервис работает в фоне...")
            .setSmallIcon(R.drawable.ic_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()


        startForeground(1, notification)

        Thread {
            for (i in 0..4) {
                Log.d(tag, "Service is running: $i")
                SystemClock.sleep(1000)
            }
            Log.d(tag, "Service finished task")
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }.start()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}