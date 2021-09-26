package com.example.myweather.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.databinding.ActivityMainWebviewBinding
import com.example.myweather.view.main.MainFragment
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // мусор
        /*binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val str = "https://gb.ru/"
        binding.editText.setText(str)

        binding.okAppCompatButton.setOnClickListener(View.OnClickListener {
            showUrl(binding.editText.text.toString())
        })
        */

        setContentView(R.layout.activity_main)

        // тут у нас открывается фрагмент, где и происходит вся движуха (single - activity)
        if(savedInstanceState == null)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()

    }

    /*@RequiresApi(Build.VERSION_CODES.N)
    fun showUrl(urlString: String) {

        val url = URL(urlString)
        // 2 вариант работы со View  - получаем указатель на текущий поток
        //val handler = Handler(Looper.myLooper()!!)
        // открываем поток для чтения
        Thread {
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            // таймаут 10 сек
            urlConnection.readTimeout = 10000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = getLines(reader)
            // 1 вариант выноса работы со View - в главный поток (более правильный)
            runOnUiThread{
                binding.webView.loadData(result, "text/html;charset=utf-8", "charset=utf-8")
            }
            // 3 вариант
            /*val handler = Handler(Looper.getMainLooper())
            handler.post{
                binding.webView.loadData(result, "text/html;charset=utf-8", "charset=utf-8")
            }*/
            urlConnection.disconnect()
        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }*/


}