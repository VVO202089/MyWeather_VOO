package com.example.myweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.databinding.FragmentMainBinding
import com.example.myweather.domain.Weather
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.MainViewModel

class DetailsFragment : Fragment() {

    // lateinit - инициализировать позже
   private lateinit var viewModel: MainViewModel

    // представление xml файла в виде кода
    private var _binding: FragmentDetailsBinding? = null // промежуточный
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    // резервация для статических методов
    companion object {

        // инициализация метода newInstance абсолютно идентичны
        /*fun newInstance():Fragment{
            return MainFragment()
        }*/

        // у нас DetailsFragment всегда получает какие - то данные.
        fun newInstance(bundle: Bundle) : DetailsFragment{
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
        val weather = (arguments?.getParcelable<Weather>(BUNDLE_WEATHER_KEY))?:Weather()
        setData(weather)
    }

    // при выходе из фрагмента (при нажатии "назад")
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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