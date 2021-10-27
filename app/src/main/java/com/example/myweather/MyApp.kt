package com.example.myweather

import android.app.Application
import androidx.room.Room
import com.example.myweather.room.HistoryDAO
import com.example.myweather.room.HistoryDataBase

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance:MyApp? = null
        private var db:HistoryDataBase? = null
        private const val DB_NAME = "HistoryDataBase"

        fun getHistoryDAO():HistoryDAO{
            if (db == null){
                if (appInstance!=null){
                    db = Room.databaseBuilder(appInstance!!.applicationContext,HistoryDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries() // работа из главного потока
                        .build()
                }else{
                    throw IllegalStateException("appInstance == null")
                }
            }
            return db!!.historyDAO()
        }
    }
}