package com.example.myweather.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.myweather.R
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.lesson10.MapsFragment
import com.example.myweather.lesson6.MainBroadcastReceiver
import com.example.myweather.lesson6.ThreadsFragment
import com.example.myweather.lesson9.ContentProviderFragment
import com.example.myweather.view.history.HistoryFragment
import com.example.myweather.view.main.MainFragment
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID_1 = "chanel_id_1"
        private const val CHANNEL_ID_2 = "chanel_id_2"
        private const val CHANNEL_ID_3 = "chanel_id_3"
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val NOTIFICATION_ID_3 = 3
    }

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

        //pushNotification()
        // FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("myLogs", it.result.toString())
            }
        }
        // registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        //registerReceiver(receiver, IntentFilter("myaction"))

        // val mySendIntent = Intent("myaction")
        //sendBroadcast(mySendIntent)

    }

    private fun pushNotification() {
        // push - уведомления
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // строим 3 нотификации
        val notificationBuilder_1 = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setContentTitle("Заголовок для канала $CHANNEL_ID_1")
            setContentText("Заголовок для канала $CHANNEL_ID_1")
            setSmallIcon(R.drawable.ic_earth)
            priority = NotificationCompat.PRIORITY_MAX
        }
        val notificationBuilder_2 = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
            setContentTitle("Заголовок для канала $CHANNEL_ID_2")
            setContentText("Заголовок для канала $CHANNEL_ID_2")
            priority = NotificationCompat.PRIORITY_MIN
            setSmallIcon(R.drawable.ic_russia)
        }
        val notificationBuilder_3 = NotificationCompat.Builder(this, CHANNEL_ID_3).apply {
            setContentTitle("Заголовок для канала $CHANNEL_ID_3")
            setContentText("Заголовок для канала $CHANNEL_ID_3")
            priority = NotificationCompat.PRIORITY_DEFAULT
            setSmallIcon(R.drawable.common_full_open_on_phone)
        }

        // проверяем версию SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // используем каналы
            // создаем первый канал
            val nameChannel_1 = "Name $CHANNEL_ID_1"
            val descChannel_1 = "Description $CHANNEL_ID_1"
            val impotanceChannel_1 = NotificationManager.IMPORTANCE_MIN
            val channel_1 =
                NotificationChannel(CHANNEL_ID_1, nameChannel_1, impotanceChannel_1).apply {
                    description = descChannel_1
                }
            // само создание
            notificationManager.createNotificationChannel(channel_1)
        }
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder_1.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // используем каналы
            // создаем второй канал
            val nameChannel_2 = "Name $CHANNEL_ID_2"
            val descChannel_2 = "Description $CHANNEL_ID_2"
            val impotanceChannel_2 = NotificationManager.IMPORTANCE_HIGH
            val channel_2 =
                NotificationChannel(CHANNEL_ID_2, nameChannel_2, impotanceChannel_2).apply {
                    description = descChannel_2
                }
            // само создание
            notificationManager.createNotificationChannel(channel_2)
        }
        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder_2.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // используем каналы
            // создаем третий канал
            val nameChannel_3 = "Name $CHANNEL_ID_3"
            val descChannel_3 = "Description $CHANNEL_ID_3"
            val impotanceChannel_3 = NotificationManager.IMPORTANCE_HIGH
            val channel_3 =
                NotificationChannel(CHANNEL_ID_3, nameChannel_3, impotanceChannel_3).apply {
                    description = descChannel_3
                }
            // само создание
            notificationManager.createNotificationChannel(channel_3)
        }
        notificationManager.notify(NOTIFICATION_ID_3, notificationBuilder_3.build())
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