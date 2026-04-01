package com.example.fz.lifecycleapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private lateinit var tvStatus: TextView
    private lateinit var etInput: EditText

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnedData = result.data?.getStringExtra("return_val")
            tvStatus.text = getString(R.string.label_status, returnedData)
            etInput.setText(returnedData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkModeSaved = prefs.getBoolean("dark_mode", false)

        if (isDarkModeSaved) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        etInput = findViewById(R.id.etInput)
        val btnGo = findViewById<Button>(R.id.btnGo)
        val btnWeb = findViewById<Button>(R.id.btnWeb)
        val themeSwitch = findViewById<SwitchMaterial>(R.id.themeSwitch)

        themeSwitch.isChecked = isDarkModeSaved

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            val editor = prefs.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()

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
    }
}