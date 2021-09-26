package com.example.myweather.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myweather.R
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.domain.Weather
import com.example.myweather.repository.WeatherDTO
import com.example.myweather.repository.WeatherLoaderListener
import com.example.myweather.repository.Weatherloader
import com.example.myweather.view.main.MainFragment
import com.example.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(),WeatherLoaderListener {

    // lateinit - инициализировать позже
   private lateinit var viewModel: MainViewModel

    // представление xml файла в виде кода
    private var _binding: FragmentDetailsBinding? = null // промежуточный
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }
    // weather с отложенной реализацией
    val localWeather:Weather by lazy {
        (arguments?.getParcelable(BUNDLE_WEATHER_KEY))?:Weather()
    }

    // резервация для статических методов
    companion object {

        // инициализация метода newInstance абсолютно идентичны
        /*fun newInstance():Fragment{
            return MainFragment()
        }*/

        // у нас DetailsFragment всегда получает какие - то данные.
        fun newInstance(bundle: Bundle) : DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
        const val BUNDLE_WEATHER_KEY = "key"
    }

    // создаем компоненты - элементы управления внутри фрагмента
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_main,container,false)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    // после onCreateView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Weatherloader(this,localWeather.city.lat,localWeather.city.lon).loadWeather()
    }

    // при выходе из фрагмента (при нажатии "назад")
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // функция заполнения данных из Weather
    private fun showWeather(weatherDTO: WeatherDTO) {

        // так как нет смысла постоянно дергать "binding"
        with(binding){
            cityName.text = localWeather.city.name;
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                localWeather.city.lat.toString(),
                localWeather.city.lon.toString()
            )
            temperatureValue.text = weatherDTO.fact.temp.toString()
            feelsLikeValue.text = weatherDTO.fact.feels_like.toString()
            binding.pressureValue.text = weatherDTO.fact.pressure_mm.toString()
            binding.windSpeedValue.text = weatherDTO.fact.wind_speed.toString()
        }

    }

    override fun onLoaded(weatherDTO: WeatherDTO) {
        showWeather(weatherDTO)
    }

    override fun onFailed(throwable: Throwable) {
        Snackbar.make(binding.root,throwable.toString(),Snackbar.LENGTH_LONG).show()
    }

    /*fun View.showSnackBarWithoutAction(stringId:Int){
        Snackbar.make(binding.root,getString(stringId),Snackbar.LENGTH_LONG).show()
    }*/

}