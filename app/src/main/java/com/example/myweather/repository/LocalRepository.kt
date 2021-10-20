package com.example.myweather.repository

import com.example.myweather.domain.Weather

interface LocalRepository {

    fun getAllHistory():List<Weather>
    fun saveEntity(weather: Weather)
}