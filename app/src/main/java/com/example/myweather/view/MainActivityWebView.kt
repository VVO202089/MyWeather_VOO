package com.example.myweather.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.lesson_1423_2_2_main.databinding.ActivityMainWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {

    lateinit var binding: ActivityMainWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.okAppCompatButton.setOnClickListener {
            showUrl(binding.editText.text.toString())
        }
    }

    fun showUrl(urlString:String){
        val url = URL(urlString)
        // 2 вариант val handler = Handler() // получаем указатель, на текущий поток
        // 2 вариант val handler = Handler(Looper.myLooper()!!) // получаем указатель, на текущий поток
        Thread{
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod ="GET"
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = getLines(reader)
            /* 1 вариант runOnUiThread {
                binding.webView.loadData(result,"text/html; charset=utf-8","utf-8")
            }*/
            // 2 вариант handler.post { binding.webView.loadData(result,"text/html; charset=utf-8","utf-8") }
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                //binding.webView.loadData(result,"text/html; charset=utf-8","utf-8")
                binding.webView.loadDataWithBaseURL(null,result,"text/html; charset=utf-8","utf-8",null)
            }
            urlConnection.disconnect()
        }.start()
    }

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


}