package com.example.myweather.view.details

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myweather.lesson6.TEST_BROADCAST_INTENT_FILTER
import com.example.myweather.lesson6.THREADS_FRAGMENT_BROADCAST_EXTRA
import com.example.myweather.repository.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.DataOutput
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// инткнт фильтр
const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"

// долгота и широта
const val LATITUDE_EXTRA = "latitude"
const val LONGITUDE_EXTRA = "longitude"

// результат
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"

class DetailsService(name: String = "name") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let{
            val lat = it.getDoubleExtra(LATITUDE_EXTRA,-1.0)
            val lon = it.getDoubleExtra(LONGITUDE_EXTRA,-1.0)
            loadWeather(lat,lon)
        }
    }

    fun loadWeather(lat:Double,lon:Double){
        val url = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        Thread{
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod ="GET"
            urlConnection.addRequestProperty("X-Yandex-API-Key","ceae3d76-b634-4bfd-8ef5-25a327758ae9")//TODO нужно разобрать вынос в файл
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO = Gson().fromJson(reader, WeatherDTO::class.java)
            val handler = Handler(Looper.getMainLooper())
            val mySendIntent = Intent(DETAILS_INTENT_FILTER )
            mySendIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA,weatherDTO)
            LocalBroadcastManager.getInstance(this).sendBroadcast(mySendIntent)

            urlConnection.disconnect()
        }.start()
    }
}