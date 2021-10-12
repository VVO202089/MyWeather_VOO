package com.example.myweather.repository

import com.example.myweather.domain.Weather

interface Repository {

    fun getWeatherFromServer(): Weather
    fun getWeatherLocalStorage(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}