package com.example.myweather.viewmodel

import com.example.myweather.domain.Weather

sealed class AppState {

    object Loading : AppState()
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
}