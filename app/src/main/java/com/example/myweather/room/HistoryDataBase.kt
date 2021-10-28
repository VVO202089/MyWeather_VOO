package com.example.myweather.room

import androidx.room.Database
import androidx.room.RoomDatabase

// таблица БД
@Database(entities = arrayOf(HistoryEntity::class),version = 1,exportSchema = false)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun historyDAO():HistoryDAO
}