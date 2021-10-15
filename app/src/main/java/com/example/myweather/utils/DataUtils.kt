package com.example.myweather.utils

import com.example.myweather.domain.City
import com.example.myweather.domain.Weather
import com.example.myweather.domain.defaultCity
import com.example.myweather.repository.FactDTO
import com.example.myweather.repository.WeatherDTO

fun convertDTOtoModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact : FactDTO = weatherDTO.fact
    return listOf(Weather(
        defaultCity(),fact.temp!!,
        fact.feels_like!!,
        fact.condition!!,
        fact.pressure_mm!!,
        fact.wind_speed!!))
}
