package com.example.myweather.domain

import android.os.Build
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.HttpURLConnection
import java.net.URL

// парселизируем класс, для дальнейшей передачи данных  в фрагмент "fragment_details" (используя аннотацию)
@Parcelize
data class Weather(
    val city: City = defaultCity(),
    val dataWeather: MutableMap<String, Int> = getDataWeather(city)
):Parcelable

private var _dataWeather: MutableMap<String, Int> = mutableMapOf()
private val URL = URL("https://api.weather.yandex.ru/v2/informers")

// по умолчанию пока только Москва, далее сделаю интеграцию с геолокацией
fun defaultCity() = City("Москва", 55.0, 37.0)

// пока без запроса к веб сервису яндекса
fun getDataWeather(city: City): MutableMap<String, Int> {

    //получим данные от Яндекс погоды, реализую позже
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

// ниже идут коллекции городов. Чтобы сэкономить память я вначале инициализирую экземпляр класса
// getDataWeather - функция, с помощью которой в будущем мы будем получать данные

// коллекция городов (зарубежных)
fun getWorldCities(): List<Weather> {

    val London = City("Лондон", 51.5085300, -0.1257400)
    val Tokio = City("Токио", 35.6895000, 139.6917100)
    val Paris = City("Париж", 48.8534100, 2.3488000)
    val Berlin = City("Берлин", 52.52000659999999, 13.404953999999975)
    val Rim = City("Рим", 41.9027835, 12.496365500000024)
    val Minsk = City("Минск", 53.90453979999999, 27.561524400000053)
    val Stambul = City("Стамбул", 41.0082376, 28.97835889999999)
    val Vashington = City("Вашингтон", 38.9071923, -77.03687070000001)
    val Kiev = City("Киев", 50.4501, 30.523400000000038)
    val Pekin = City("Пекин", 39.90419989999999, 116.40739630000007)

    return listOf(
        Weather(London, getDataWeather(London)),
        Weather(Tokio, getDataWeather(Tokio)),
        Weather(Paris, getDataWeather(Paris)),
        Weather(Berlin, getDataWeather(Berlin)),
        Weather(Rim, getDataWeather(Rim)),
        Weather(Minsk, getDataWeather(Minsk)),
        Weather(Stambul, getDataWeather(Stambul)),
        Weather(Vashington, getDataWeather(Vashington)),
        Weather(Kiev, getDataWeather(Kiev)),
        Weather(Pekin, getDataWeather(Pekin))
    )

}

// получим коллекцмю российских городов
fun getRussianCities(): List<Weather> {

    val Moscow = City("Москва", 55.755826, 37.617299900000035)
    val Spb = City("Санкт-Петербург", 59.9342802, 30.335098600000038)
    val Nsk = City("Новосибирск", 55.00835259999999, 82.93573270000002)
    val Ekat = City("Екатеринбург", 56.83892609999999, 60.60570250000001)
    val Nnovg = City("Нижний Новгород", 56.2965039, 43.936059)
    val Kazan = City("Казань", 55.8304307, 49.06608060000008)
    val Chelyab = City("Челябинск", 55.1644419, 61.4368432)
    val Omsk = City("Омск", 54.9884804, 73.32423610000001)
    val Rostov = City("Ростов-на-Дону", 47.2357137, 39.701505)
    val Upha = City("Уфа", 54.7387621, 55.972055400000045)

    return listOf(
        Weather(Moscow, getDataWeather(Moscow)),
        Weather(Spb, getDataWeather(Spb)),
        Weather(Nsk, getDataWeather(Nsk)),
        Weather(Ekat, getDataWeather(Ekat)),
        Weather(Nnovg, getDataWeather(Nnovg)),
        Weather(Kazan, getDataWeather(Kazan)),
        Weather(Chelyab, getDataWeather(Chelyab)),
        Weather(Omsk, getDataWeather(Omsk)),
        Weather(Rostov, getDataWeather(Rostov)),
        Weather(Upha, getDataWeather(Upha))
    )
}

// получение температуры (пока без запросов к яндексу)
fun getTemperature(city: City): Int {
    return 7
}

// получение "Ощущается" (пока без запросов к яндексу)
fun getFeelsLike(city: City): Int {
    return 9
}

// получение давление в мм рт ст (пока без запроса в яндекс)
fun getPressure(city: City): Int {
    return 646
}






