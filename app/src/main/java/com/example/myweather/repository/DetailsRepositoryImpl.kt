package com.example.myweather.repository

import okhttp3.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    // уже не актиуально
    /*
    override fun getWeatherDetailsFromServer(requestLink: String, callback: Callback) {
        remoteDataSource.getWeatherDetails(requestLink, callback)
    }
     */

    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetails(lat,lon,callback)
    }


}