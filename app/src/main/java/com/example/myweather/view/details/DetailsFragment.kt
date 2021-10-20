package com.example.myweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.myweather.databinding.FragmentDetailsBinding
import com.example.myweather.domain.Weather
import com.example.myweather.repository.WeatherDTO
import com.example.myweather.utils.show
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
import okhttp3.*
import java.nio.file.WatchEvent

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    private val translations = mapOf(
        "clear" to "Ясно",
        "partly-cloudy" to "Малооблачно",
        "cloudy" to "Облачно с прояснениями",
        "overcast" to "Пасмурно",
        "drizzle" to "Морось",
        "light-rain" to "Небольшой дождь",
        "rain" to "Дождь",
        "moderate-rain" to "Умеренно сильный дождь",
        "heavy-rain" to "Сильный дождь",
        "continuous-heavy-rain" to "Длительный сильный дождь",
        "showers" to "Ливень",
        "wet-snow" to "Дождь со снегом",
        "light-snow" to "Небольшой снег",
        "snow" to "Снег",
        "snow-showers" to "Снегопад",
        "hail" to "Град",
        "thunderstorm" to "Гроза",
        "thunderstorm-with-rain" to "Дождь с грозой",
        "thunderstorm-with-hail" to "Гроза с градом",
        "night" to "Ночь",
        "morning" to "Утро",
        "day" to "День",
        "evening" to "вечер"
    )

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
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val localWeather: Weather by lazy {
        (arguments?.getParcelable(BUNDLE_WEATHER_KEY)) ?: Weather()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
       getWeather()
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error ->{
                binding.loadingLayout.visibility = View.INVISIBLE
                binding.mainView.visibility = View.VISIBLE
                val throbable = appState.error
                //Snackbar.make(binding.root,"$throbable",Snackbar.LENGTH_LONG).show()
                binding.root.show("ERROR $throbable","RELOAD",{
                    getWeather()
                })
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.mainView.visibility = View.INVISIBLE
            }
            is AppState.SuccessMain -> {
                binding.loadingLayout.visibility = View.INVISIBLE
                binding.mainView.visibility = View.VISIBLE
                val weatherData = appState.weatherData
                showWeather(weatherData[0])
                //Snackbar.make(binding.root,"Success",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun getWeather(){
        viewModel.getWeatherFromRemoteSource(localWeather.city.lat,localWeather.city.lon)
    }

    /*fun getWeather() {
        // инициализируем подключение
        val client = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
        builder.header(BuildConfig.YANDEX_API_KEY, BuildConfig.YANDEX_API_VALUE)
        builder.url(YANDEX_API_URL + YANDEX_API_URL_END_POINT + "?lat" + "=${localWeather.city.lat}&lon=${localWeather.city.lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        // ставим в очередь запросы, асихронно будет выполняться
        // асинхронно
        /*call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                // проверка результата запроса
                val serverResponse:String?= response.body?.string()
                if (response.isSuccessful && serverResponse != null) {
                    val weatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                    // запуск должен проитсходить в главном потоке, иначе не будет доступа к вьюхам
                    Handler(Looper.getMainLooper()).post{
                        showWeather(weatherDTO)
                    }

                }
            }

        })
         */
        // есть способ более простой
        // синхронно
        Thread {
            // дело 1
            val response: Response = call.execute()
            val serverResponse: String? = response.body?.string()
            if (response.isSuccessful && serverResponse != null) {
                val weatherDTO = Gson().fromJson(serverResponse, WeatherDTO::class.java)
                // запуск должен происходить в главном потоке, иначе не будет доступа к вьюхам
                Handler(Looper.getMainLooper()).post {
                    showWeather(weatherDTO)
                }
            }
            // Дело 2
        }.start()
    }

     */

    private fun showWeather(weather: Weather) {
        // сохраняем элемент погоды
        viewModel.saveWeather(Weather(localWeather.city,
            localWeather.temp,
            localWeather.feelsLike,
            localWeather.condition,
            localWeather.pressuremm,
            localWeather.windSpeed))
        // разобрать
        with(binding) {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "lat ${localWeather.city.lat}\n lon ${localWeather.city.lon}"
            temperatureValue.text = weather.temp.toString()
            feelsLikeValue.text = "${weather.feelsLike}"
            weatherCondition.text = "${translations[weather.condition]}"
            pressureValue.text = "${weather.pressuremm}"
            windSpeedValue.text = "${weather.windSpeed}"
            //ImageView
            /*Picasso
                .get()
                .load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg")
                .into(binding.ivHeader)
            Glide
                .with(binding.ivHeader)
                .load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg")
                .into(binding.ivHeader)*/

            // самая простая реализация
            binding.ImageView.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            //binding.iv.loadUrl("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}