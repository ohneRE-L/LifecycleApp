package com.example.fz.lifecycleapp

import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(private val catimgDao: CatimgDao) {

    val allCats: LiveData<List<Model>> = catimgDao.getAllCats()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val catApi = retrofit.create(CATapi::class.java)

    suspend fun fetchAndSaveCats(limit: Int) {
        try {
            val cats = catApi.getCats(limit)
            // In a real app, we might want to clear or update intelligently
            // For this lab, we just insert the new ones
            for (cat in cats) {
                catimgDao.insert(cat)
            }
        } catch (e: Exception) {
            // Handle error (e.g., no network)
        }
    }

    suspend fun deleteAll() {
        catimgDao.deleteAll()
    }
}
