package com.example.myweather.viewmodel

import com.example.myweather.domain.Weather
import com.example.myweather.repository.WeatherDTO

sealed class AppState {

    object Loading : AppState()
    data class SuccessMain(val weatherData: List<Weather>) : AppState()
    data class SuccessDetails(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
}