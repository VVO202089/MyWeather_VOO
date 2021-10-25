package com.example.myweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.MyApp
import com.example.myweather.repository.LocalRepository
import com.example.myweather.repository.LocalRepositoryImpl

// константы
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class HistoryViewModel(

    //val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyLiveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepositotyImpl: LocalRepository = LocalRepositoryImpl(
        MyApp.getHistoryDAO()
    )
) : ViewModel() {

    fun getLiveData() = historyLiveDataToObserver

    fun getAllHistory(){
        historyLiveDataToObserver.value = AppState.Loading;
        historyLiveDataToObserver.value = AppState.SuccessMain(historyRepositotyImpl.getAllHistory());

    }

    fun deleteAll() = historyRepositotyImpl.deleteAll()

}
