package com.example.fz.lifecycleapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val catViewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = CatAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        catViewModel.allCats.observe(this) { cats ->
            adapter.setCats(cats)
        }

        findViewById<Button>(R.id.buttonFetch).setOnClickListener {
            catViewModel.fetchCats(10)
        }

        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            catViewModel.clearCats()
        }
    }
}
