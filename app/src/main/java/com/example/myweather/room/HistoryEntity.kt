package com.example.myweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// сущность
@Entity
data class HistoryEntity(
    // уникальное поле
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val temp: Int,
    val condition: String
)

