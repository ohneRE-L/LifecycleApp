package com.example.fz.lifecycleapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "onCreate called")

        editText = findViewById(R.id.etInput)
        val btnGo = findViewById<Button>(R.id.btnGo)

        btnGo.setOnClickListener {
            val text = editText.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("user_input", text)
            startActivity(intent)
        }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(tag, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(tag, "onStop called") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "onDestroy called") }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("saved_text", editText.text.toString())
        Log.d(tag, "onSaveInstanceState called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedText = savedInstanceState.getString("saved_text")
        editText.setText(savedText)
        Log.d(tag, "onRestoreInstanceState called")
    }
}