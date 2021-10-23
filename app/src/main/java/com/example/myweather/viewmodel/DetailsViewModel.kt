package com.example.myweather.viewmodel

import android.net.sip.SipErrorCode.SERVER_ERROR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.MyApp
import com.example.myweather.domain.Weather
import com.example.myweather.repository.*
import com.example.myweather.utils.convertDTOtoModel

// константы
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(

    //val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsLiveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val historyRepositotyImpl:LocalRepository = LocalRepositoryImpl(
        MyApp.getHistoryDAO()
    )
) : ViewModel() {

    fun saveWeather(weather: Weather){
        historyRepositotyImpl.saveEntity(weather)
    }

    // более удобная запись
    fun getLiveData() = detailsLiveDataToObserver

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveDataToObserver.value = AppState.Loading
        //detailsRepositoryImpl.getWeatherDetailsFromServer(requestLink, callback)
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callback)
    }

    private val callback = object : retrofit2.Callback<WeatherDTO> {

        // успешно
        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {

            if(response.isSuccessful&&response.body()!=null){
                val weatherDTO = response.body()
                weatherDTO?.let{
                    detailsLiveDataToObserver.postValue( AppState.SuccessMain(convertDTOtoModel(weatherDTO)))
                }
            }else{
                // TODO HW   detailsLiveDataToObserve.postValue( AppState.Error("dfhgerh"))
            }
        }

        // неудачно
        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            detailsLiveDataToObserver.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

    }

}

/*
private val callback = object : Callback {
    override fun onFailure(call: Call, e: IOException) {
        // доделать самому
    }

    override fun onResponse(call: Call, response: Response) {
        // проверка результата запроса
        val serverResponse: String? = response.body?.string()
        if (response.isSuccessful && serverResponse != null) {
            val weatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
            // запуск должен проитсходить в главном потоке, иначе не будет доступа к вьюхам
            detailsLiveDataToObserver.postValue(AppState.Success(convertDTOtoModel(weatherDTO)))
        } else {
            // доделать самому
        }


    }
}
fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

// эмуляция работы сервера (задержка)
fun getDataFromLocalSource(isRussian:Boolean) {

    detailsLiveDataToObserver.postValue(AppState.Loading)
    Thread {
        sleep(1000)
        detailsLiveDataToObserver.postValue(AppState.Success(
            if (isRussian) detailsRepositoryImpl.getWeatherFromLocalStorageRus() else
                detailsRepositoryImpl.getWeatherFromLocalStorageWorld()
        ))
    }.start()

}
*/