package com.example.fz.lifecycleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    val allCats: LiveData<List<Model>>

    init {
        val catDao = CatDatabase.getDatabase(application).catimgDao()
        repository = Repository(catDao)
        allCats = repository.allCats
    }

    fun fetchCats(limit: Int) = viewModelScope.launch {
        repository.fetchAndSaveCats(limit)
    }

    fun clearCats() = viewModelScope.launch {
        repository.deleteAll()
    }
}
