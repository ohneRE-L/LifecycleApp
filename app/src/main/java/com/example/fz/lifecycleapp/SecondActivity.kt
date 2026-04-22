package com.example.fz.lifecycleapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private val tag = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val etEdit = findViewById<EditText>(R.id.etEdit)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val incomingText = intent.getStringExtra("user_data")
        etEdit.setText(incomingText)

        btnSave.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("return_val", etEdit.text.toString())
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart") }
}