package com.example.myweather.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val MAIN_SERVICE_STRING_EXTRA  = "MainServiceExtra"
// запуск сервиса в отдельном потоке
class MainService(name:String = "name"):IntentService(name) {

    override fun onHandleIntent(p0: Intent?) {
        createLoadMessage("onHandleIntent ${p0?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")

        val mySendIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        mySendIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA,"answer ${(0..100).random()}")
        //sendBroadcast(mySendIntent)
        LocalBroadcastManager.getInstance(this).sendBroadcast(mySendIntent)
    }

    override fun onCreate() {
        createLoadMessage("OnCreate")
         super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLoadMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLoadMessage("onDestroy")
        super.onDestroy()
    }

    private fun createLoadMessage(message: String) {
        //createLoadMessage("createLogMessage")
        Log.d("myLog",message)
    }
}