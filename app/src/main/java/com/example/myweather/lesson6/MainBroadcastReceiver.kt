package com.example.myweather.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        val text = p1?.action
        Log.d("myLogs", text.toString())
        Toast.makeText(p0!!, text, Toast.LENGTH_LONG).show()
    }


}