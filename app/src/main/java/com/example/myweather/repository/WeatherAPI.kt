package com.example.myweather.repository

import com.example.myweather.BuildConfig.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    // получение погоды
    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

    // для примера
    @GET(YANDEX_API_URL_END_POINT_FACT)
    fun getFact():Call<FactDTO>

    @GET(YANDEX_API_URL_END_POINT_IMAGE)
    fun getImage()


}