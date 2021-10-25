package com.example.myweather.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.lesson10.MapsFragment
import com.example.myweather.lesson6.MainBroadcastReceiver
import com.example.myweather.lesson6.ThreadsFragment
import com.example.myweather.lesson9.ContentProviderFragment
import com.example.myweather.view.history.HistoryFragment
import com.example.myweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // мусор
        /*binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val str = "https://gb.ru/"
        binding.editText.setText(str)

        binding.okAppCompatButton.setOnClickListener(View.OnClickListener {
            showUrl(binding.editText.text.toString())
        })
        */

        //setContentView(R.layout.activity_main)

        // тут у нас открывается фрагмент, где и происходит вся движуха (single - activity)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance())
                .commit()

        // registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        //registerReceiver(receiver, IntentFilter("myaction"))

        // val mySendIntent = Intent("myaction")
        //sendBroadcast(mySendIntent)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_fragment_threads -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ThreadsFragment.newInstance()).commit()
                true
            }
            R.id.action_open_fragment_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HistoryFragment.newInstance())
                    .addToBackStack("").commit()
                true
            }
            R.id.action_open_fragment_content_provider -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ContentProviderFragment.newInstance())
                    .addToBackStack("").commit()
                true
            }
            R.id.action_open_fragment_menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MapsFragment.newInstance())
                    .addToBackStack("").commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}