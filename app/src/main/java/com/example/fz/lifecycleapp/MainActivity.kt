package com.example.fz.lifecycleapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private lateinit var tvStatus: TextView
    private lateinit var etInput: EditText

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnedData = result.data?.getStringExtra("return_val")
            tvStatus.text = getString(R.string.label_status, returnedData)
            etInput.setText(returnedData)
            Log.d(tag, "Результат получен: $returnedData")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        etInput = findViewById(R.id.etInput)
        val btnGo = findViewById<Button>(R.id.btnGo)
        val btnWeb = findViewById<Button>(R.id.btnWeb)

        btnGo.setOnClickListener {
            val textToSend = etInput.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("user_data", textToSend)
            startForResult.launch(intent)
        }

        btnWeb.setOnClickListener {
            val webpage: Uri = Uri.parse("https://developer.android.com/guide/components/intents-filters")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume") }
}