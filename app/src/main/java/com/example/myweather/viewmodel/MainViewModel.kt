package com.example.myweather.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.repository.Repository
import com.example.myweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    /*fun getLiveData():LiveData<Any>{
        return liveDataToObserver
    }*/

    // более удобная запись
    fun getLiveData() = liveDataToObserver

    fun getWeatherFromLocalSource() = getDataFromRemoteSource()

    fun getWeatherFromRemoteSource() = getDataFromRemoteSource()

    fun getDataFromRemoteSource() {

        liveDataToObserver.postValue(AppState.Loading)
        Thread {
            sleep(2000)
            liveDataToObserver.postValue(AppState.Success(repositoryImpl.getWeatherLocalStorage()))
        }.start()

    }
}