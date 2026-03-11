package com.example.fz.lifecycleapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private val tag = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d(tag, "onCreate called")
        val tvOutput = findViewById<TextView>(R.id.tvOutput)
        val receivedText = intent.getStringExtra("user_input")
        if (receivedText != null) {
            tvOutput.text = receivedText
        } else {
            tvOutput.text = "Данные не получены"
        }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(tag, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(tag, "onStop called") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "onDestroy called") }
}