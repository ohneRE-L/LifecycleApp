package com.example.fz.lifecycleapp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {
    private val tag = "MyService"

    override fun onCreate() {
        super.onCreate()
        // Настройка уведомления для работы на переднем плане
        val notification: Notification = NotificationCompat.Builder(this, "service_channel")
            .setContentTitle("My Serevic")
            .setContentText("Выполнение фоновой задачи...")
            .setSmallIcon(R.drawable.ic_info)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (i in 0..4) {
                Log.d(tag, "Service is running: $i")
                SystemClock.sleep(1000)
            }
            stopSelf() // Останавливаем сервис по завершении
        }.start()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}