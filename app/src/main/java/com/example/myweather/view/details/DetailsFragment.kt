package com.example.myweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myweather.domain.Weather
import com.example.myweather.repository.WeatherDTO
import com.example.myweather.repository.WeatherLoaderListener
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.lesson_1423_2_2_main.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(), WeatherLoaderListener {

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val weatherDTO = it.getParcelableExtra<WeatherDTO>(DETAILS_LOAD_RESULT_EXTRA)
                if (weatherDTO != null) {
                    showWeather(weatherDTO)
                } else {
                    Snackbar.make(binding.mainView,"ошибка при получении данных!",Snackbar.LENGTH_LONG).show()
                }
            }

        }
    }

    val listener = object : WeatherLoaderListener {
        override fun onLoaded(weatherDTO: WeatherDTO) {
        }

        override fun onFailed(throwable: Throwable) {
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val BUNDLE_WEATHER_KEY = "key"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_main, container, false)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val localWeather: Weather by lazy {
        (arguments?.getParcelable(BUNDLE_WEATHER_KEY)) ?: Weather()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //WeatherLoader(this, localWeather.city.lat, localWeather.city.lon).loadWeather()
        val intent = Intent(requireActivity(), DetailsService::class.java)
        intent.putExtra(LATITUDE_EXTRA, localWeather.city.lat)
        intent.putExtra(LONGITUDE_EXTRA, localWeather.city.lon)
        requireActivity().startService(intent)
        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(receiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    private fun showWeather(weatherDTO: WeatherDTO) {

        with(binding) {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}\n lon ${localWeather.city.lon}"
            temperatureValue.text = weatherDTO.fact.temp.toString()
            feelsLikeValue.text = "${weatherDTO.fact.feels_like}"
            weatherCondition.text = "${weatherDTO.fact.condition}"
            pressureValue.text = "${weatherDTO.fact.pressure_mm}"
            windSpeedValue.text = "${weatherDTO.fact.wind_speed}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onLoaded(weatherDTO: WeatherDTO) {
        showWeather(weatherDTO)
    }

    override fun onFailed(throwable: Throwable) {
        // не получилось пробросить исключение
    }

}