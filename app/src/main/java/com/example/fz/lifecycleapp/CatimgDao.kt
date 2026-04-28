package com.example.fz.lifecycleapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatimgDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cat: Model)

    @Query("SELECT * FROM catimg")
    fun getAllCats(): LiveData<List<Model>>

    @Query("DELETE FROM catimg")
    suspend fun deleteAll()
}
