package com.example.myweather.domain

import android.os.Build
import java.net.HttpURLConnection
import java.net.URL

data class Weather(
    val city: City = defaultCity(),
    val dataWeather: MutableMap<String, Int> = getDataWeather(city)
)

private var _dataWeather: MutableMap<String, Int> = mutableMapOf()
private val URL = URL("https://api.weather.yandex.ru/v2/informers")

// по умолчанию пока только Москва, далее сделаю интеграцию с геолокацией
fun defaultCity() = City("Москва", 55.0, 37.0)

// пока без запроса к веб сервису яндекса
fun getDataWeather(city: City): MutableMap<String, Int> {

    //получим данные от Яндекс погоды
    /*with(URL.openConnection() as HttpURLConnection){
        requestMethod = "GET"
        inputStream.bufferedReader().use {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.lines().forEach { line -> println(line) }
            }
        }
    }*/
    // пока значения рандомные
    _dataWeather.put("temperature", getTemperature(city))
    _dataWeather.put("feelsLike", getFeelsLike(city))
    _dataWeather.put("pressure", getPressure(city))

    return _dataWeather

}

// получение температуры (пока без запросов к яндексу)
fun getTemperature(city: City): Int {
    return 7
}

// получение "Ощущается" (пока без запросов к яндексу)
fun getFeelsLike(city: City): Int {
    return 9
}

// получение давление в мм рт ст
fun getPressure(city: City): Int {
    return 646
}






