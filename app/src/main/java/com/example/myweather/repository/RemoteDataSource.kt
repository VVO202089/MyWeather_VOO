package com.example.myweather.repository

import com.example.myweather.BuildConfig
import com.example.myweather.BuildConfig.*
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    // стандартная штука, инициализирцем Api
    private val weatherApi by lazy {

        Retrofit.Builder()
            .baseUrl(YANDEX_API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build().create(WeatherAPI::class.java)
    }

        /*
    // уже не нужно
    fun getWeatherDetails(requestLink: String, callback: Callback) {
    val client = OkHttpClient()
    val builder: Request.Builder = Request.Builder()
    builder.header(BuildConfig.YANDEX_API_KEY, BuildConfig.YANDEX_API_VALUE)
    builder.url(requestLink)
    val request: Request = builder.build()
    val call: Call = client.newCall(request)
    call.enqueue(callback)
    }
     */

    // перечень запросов из интерфейса WeatherApi
        fun getWeatherDetails(lat: Double, lon: Double, Callback: Callback<WeatherDTO>) {
            weatherApi.getWeather(YANDEX_API_VALUE,lat,lon).enqueue(Callback)
        }
    }