package com.example.fz.lifecycleapp

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private lateinit var tvStatus: TextView
    private lateinit var etInput: EditText
    private var boundService: BoundService? = null
    private var isBound = false

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnedData = result.data?.getStringExtra("return_val")
            tvStatus.text = getString(R.string.label_status, returnedData)
            etInput.setText(returnedData)
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true

            val data = boundService?.getData()
            Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkModeSaved = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeSaved) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        etInput = findViewById(R.id.etInput)
        val btnGo = findViewById<Button>(R.id.btnGo)
        val btnWeb = findViewById<Button>(R.id.btnWeb)
        val themeSwitch = findViewById<SwitchMaterial>(R.id.themeSwitch)

        createNotificationChannel()
        checkPermissions()

        themeSwitch.isChecked = isDarkModeSaved
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            recreate()
        }

        btnGo.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("user_data", etInput.text.toString())
            startForResult.launch(intent)
        }

        btnWeb.setOnClickListener {
            val webpage: Uri = Uri.parse("https://developer.android.com")
            startActivity(Intent(Intent.ACTION_VIEW, webpage))
        }

        btnGo.setOnLongClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
            true
        }

        btnWeb.setOnLongClickListener {
            val intent = Intent(this, BoundService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            true
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "service_channel", "Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}