package com.example.myweather.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.databinding.FragmentMainBinding
import com.example.myweather.domain.City
import com.example.myweather.domain.Weather
import com.example.myweather.view.OnItemViewClickListener
import com.example.myweather.view.details.DetailsFragment
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

private const val IS_WORLD_KEY = "LIST_OF_TOWNS_KEY"
private const val REQUEST_CODE = 12345
private const val REFRESH_PERIOD = 10000L
private const val MINIMAL_DISTANCE = 100f

class MainFragment : Fragment(), OnItemViewClickListener {

    // lateinit - инициализировать позже
    private lateinit var viewModel: MainViewModel

    val REQUEST_CODE = 999;

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
        binding.mainFragmentFABLocation.setOnClickListener {
            checkPermission()
        }
        binding.mainFragmentFAB.setOnClickListener {
            isDataSetRus = !isDataSetRus
            //viewModel.getDataFromLocalSource(isDataSetRus)
            // установим картинку
            if (isDataSetRus) {
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            } else {
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
            is AppState.SuccessMain -> {
                val weatherData = appState.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
                binding.root.showSnackBarWithoutAction(R.string.completed)
            }
        }
    }

    fun View.showSnackBarWithoutAction(stringId: Int) {
        Snackbar.make(binding.root, getString(stringId), Snackbar.LENGTH_LONG).show()
    }

    // обработчик нажатия кнопки в списке городов (из адаптера)
    override fun onItemClick(weather: Weather) {

        val bundle = Bundle();
        bundle.putParcelable(DetailsFragment.BUNDLE_WEATHER_KEY, weather)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                DetailsFragment.newInstance(bundle)
            ).addToBackStack("").commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    getRatio()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkPermission() {
        context?.let {
            // обращение к Permission из манифеста (проверка на доступ)
            if (ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLocation()
                // запрос на рационализацию
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                getRatio()
            } else {
                myRequestPermission()
            }
        }
    }

    private fun getRatio() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_rationale_title)
            .setMessage(R.string.dialog_rationale_message)
            .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { dialog, which ->
                myRequestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }

    private val onLocationChangeListener = object : LocationListener {

        override fun onLocationChanged(p0: Location) {
            getAddressAsync(requireContext(), p0)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

    }

    private fun getAddressAsync(context: Context, p0: Location) {
        val geocoder = Geocoder(context)
        val address = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
        showAddressDialog(address[0].getAddressLine(0), p0)
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    onItemClick(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    fun getLocation() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                // если провайдер включен
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, REFRESH_PERIOD,
                            MINIMAL_DISTANCE, onLocationChangeListener
                        )
                    }

                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    location?.let {
                        // TODO показать диалог с координатами
                    }
                }
            } else {
                getRatio()
            }
        }
    }

    fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

}