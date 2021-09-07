package com.example.myweather.repository

import com.example.myweather.domain.Weather

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherLocalStorage(): Weather {
        return Weather()
    }
}