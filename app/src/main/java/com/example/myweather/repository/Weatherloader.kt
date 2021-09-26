package com.example.myweather.repository

import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.URL
import java.util.logging.Handler
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class Weatherloader(val listener: WeatherLoaderListener, val lat: Double, val lon: Double) {

    fun loadWeather() {

        // пока харкодинг
        val urlString = "https://api.weather.yandex.ru/v2/informers/?lat=${lat}&lon=${lon}"
        val url = URL(urlString)
        // открываем поток для чтения
        Thread {
            try {
                val urlConnection = url.openConnection() as HttpsURLConnection
                // тип метода
                urlConnection.requestMethod = "GET"
                // установка ключа
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    "154d332f-ec40-43a8-8068-810a4682f544"
                )
                // таймаут 10 сек
                urlConnection.readTimeout = 10000
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                // парсим ответ
                //WeatherDTO::class.java - ссылка на класс
                val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
                val handler = android.os.Handler(Looper.getMainLooper())
                handler.post {
                    listener.onLoaded(weatherDTO)
                }
                urlConnection.disconnect()
            } catch (e: ConnectException) {
                listener.onFailed(ConnectException(e.toString()))
            }
        }.start()
    }
}