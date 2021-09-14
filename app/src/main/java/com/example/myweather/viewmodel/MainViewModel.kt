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

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    fun getWeatherFromRemoteSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    // эмуляция работы сервера (задержка)
    fun getDataFromLocalSource(isRussian:Boolean) {

        liveDataToObserver.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            liveDataToObserver.postValue(AppState.Success(
                if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus() else
                    repositoryImpl.getWeatherFromLocalStorageWorld()
            ))
        }.start()

    }
}