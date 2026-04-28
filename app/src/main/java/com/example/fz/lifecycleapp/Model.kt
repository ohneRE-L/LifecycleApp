package com.example.fz.lifecycleapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "catimg", indices = [Index(value = ["id"], unique = true)])
data class Model(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: String,

    @SerializedName("url")
    @ColumnInfo(name = "image")
    val url: String
)
