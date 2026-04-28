package com.example.fz.lifecycleapp

import retrofit2.http.GET
import retrofit2.http.Query

interface CATapi {
    @GET("images/search")
    suspend fun getCats(@Query("limit") limit: Int): List<Model>
}
