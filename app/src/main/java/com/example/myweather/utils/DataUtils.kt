package com.example.myweather.utils

import com.example.myweather.domain.City
import com.example.myweather.domain.Weather
import com.example.myweather.domain.defaultCity
import com.example.myweather.repository.WeatherDTO
import com.example.myweather.room.HistoryEntity

// конвертер DTO  в модель
fun convertDTOtoModel(weatherDTO: WeatherDTO): List<Weather> {
    return listOf(Weather(
        defaultCity(),weatherDTO.fact.temp!!,
        weatherDTO.fact.feels_like!!,
        weatherDTO.fact.condition!!,
        weatherDTO.fact.pressure_mm!!,
        weatherDTO.fact.wind_speed!!,
        weatherDTO.fact.icon))
}

// конвертер Entity в Weather
fun convertHistoryEntityToWeather(entityList:List<HistoryEntity>): List<Weather> {
   return entityList.map {
       Weather(
           City(it.name, 0.0,0.0),
           it.temp,
           it.feelsLike,
           it.condition,
           it.pressuremm,
       it.windSpeed)
   }
}

// конвертер из Weather в Entity
fun convertWeatherToHistoryEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0,
        weather.city.name,
        weather.temp,
        weather.feelsLike,
        weather.condition,
        weather.pressuremm,
        weather.windSpeed)
}
