package com.example.myweather.repository

import com.example.myweather.domain.Weather
import com.example.myweather.room.HistoryDAO
import com.example.myweather.utils.convertHistoryEntityToWeather
import com.example.myweather.utils.convertWeatherToHistoryEntity

class LocalRepositoryImpl(val localDataSource: HistoryDAO):LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToHistoryEntity(weather))
    }

    override fun deleteAll(): List<Weather> {
        localDataSource.deleteAll()
        return listOf()
    }


}