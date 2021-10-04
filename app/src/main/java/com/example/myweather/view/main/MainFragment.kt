package com.example.myweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.domain.Weather
import com.example.myweather.view.OnItemViewClickListener
import com.example.myweather.view.details.DetailsFragment
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.lesson_1423_2_2_main.R
import ru.geekbrains.lesson_1423_2_2_main.databinding.FragmentMainBinding

class MainFragment : Fragment(),OnItemViewClickListener {

    // lateinit - инициализировать позже
    private lateinit var viewModel: MainViewModel

    // представление xml файла в виде кода
    private var _binding: FragmentMainBinding? = null // промежуточный
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private var isDataSetRus: Boolean = true
    private var adapter = MainFragmentAdapter()

    // резервация для статических методов
    companion object {

        // метод, который используется для создания экземпляра класса
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainFragmentRecyclerView.adapter = adapter
        adapter.setOnItemViewClickListener(this)
        binding.mainFragmentFAB.setOnClickListener {
            isDataSetRus = !isDataSetRus
            //viewModel.getDataFromLocalSource(isDataSetRus)
            // установим картинку
            if (isDataSetRus){
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            }else{
                viewModel.getWeatherFromLocalSourceWorld()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        // инициализация
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // подписка на событие изменение и в зависимости от того, какой у нас результат
        // выполняем нужные действия
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })
        viewModel.getWeatherFromLocalSourceRus()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> TODO()
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                binding.root.showSnackBarWithoutAction(R.string.goLoad)
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
                binding.root.showSnackBarWithoutAction(R.string.completed)
            }
        }
    }

    fun View.showSnackBarWithoutAction(stringId:Int){
        Snackbar.make(binding.root,getString(stringId),Snackbar.LENGTH_LONG).show()
    }

    // обработчик нажатия кнопки в списке городов (из адаптера)
    override fun onItemClick(weather: Weather) {

        val bundle = Bundle();
        bundle.putParcelable(DetailsFragment.BUNDLE_WEATHER_KEY,weather)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,
            DetailsFragment.newInstance(bundle)).addToBackStack("").commit()
    }

    // функция заполнения данных из Weather
    /*private fun setData(weatherData: List<Weather>) {
        binding.cityName.text = weatherData.city.name;
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        binding.temperatureValue.text = weatherData.dataWeather.get("temperature").toString()
        binding.feelsLikeValue.text = weatherData.dataWeather.get("feelsLike").toString()
        binding.pressureValue.text = weatherData.dataWeather.get("pressure").toString()
    }*/

}