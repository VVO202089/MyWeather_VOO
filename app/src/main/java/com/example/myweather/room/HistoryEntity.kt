package com.example.myweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// сущность
@Entity
data class HistoryEntity(
    // уникальное поле
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var temp: Int = 0,
    var feelsLike: Int = 0,
    var condition: String = "",
    var pressuremm:Int =  0,
    var windSpeed:Double = 0.0
)

