package com.example.myweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.databinding.FragmentMainBinding
import com.example.myweather.domain.Weather
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    // lateinit - инициализировать позже
    private lateinit var viewModel: MainViewModel

    // представление xml файла в виде кода
    private var _binding: FragmentMainBinding? = null // промежуточный
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    // резервация для статических методов
    companion object {

        // инициализация метода newInstance абсолютно идентичны
        /*fun newInstance():Fragment{
            return MainFragment()
        }*/
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_main,container,false)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // инициализация
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // подписка на событие изменение и в зависимости от того, какой у нас результат
        // выполняем нужные действия
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getDataFromRemoteSource()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> TODO()
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                //binding.message.setText("Пошла загрузка")
                //Snackbar.make(binding.message,"Пошла загрузка",Snackbar.LENGTH_LONG).show()
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                setData(weatherData)
                //binding.message.setText("Готово")
                //Snackbar.make(binding.message,"Готово",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    // функция заполнения данных из Weather
    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.city.name;
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        binding.temperatureValue.text = weatherData.dataWeather.get("temperature").toString()
        binding.feelsLikeValue.text = weatherData.dataWeather.get("feelsLike").toString()
        binding.pressureValue.text = weatherData.dataWeather.get("pressure").toString()
    }

}