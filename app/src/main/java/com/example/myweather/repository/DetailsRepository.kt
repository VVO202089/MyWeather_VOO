package com.example.myweather.repository

import okhttp3.Callback
import retrofit2.Retrofit


interface DetailsRepository {

    // хардкорная передача requestLink - это не правильно
    //fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
    // используя retrofit
    fun getWeatherDetailsFromServer(lat:Double,lon:Double,callback: retrofit2.Callback<WeatherDTO>)
}