package com.example.myweather.view.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.R
import com.example.myweather.databinding.FragmentHistoryBinding
import com.example.myweather.domain.Weather
import com.example.myweather.viewmodel.AppState
import com.example.myweather.viewmodel.HistoryViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment() {

    // lateinit - инициализировать позже
    private val viewModel by lazy{
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    // представление xml файла в виде кода
    private var _binding: FragmentHistoryBinding? = null // промежуточный
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

    private val adapter:HistoryAdapter by lazy{
        HistoryAdapter()
    }

    private val listWeatherData : List<Weather>
    get(){
        return listWeatherData
    }

    // резервация для статических методов
    companion object {

        // метод, который используется для создания экземпляра класса
        fun newInstance() = HistoryFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner,{
            renderData((it))
        })
        viewModel.getAllHistory()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_history,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_clear_history ->{
                viewModel.deleteAll()
                adapter.setWeather(listOf())
            }
        }

        return super.onOptionsItemSelected(item)
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
                binding.root.showSnackBarWithoutAction(R.string.goLoad)
            }
            is AppState.Loading ->{
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessMain -> {
                binding.historyFragmentRecyclerview.adapter = adapter
                val listWeatherData = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                adapter.setWeather(listWeatherData)
                binding.root.showSnackBarWithoutAction(R.string.completed)
            }
        }
    }

    fun View.showSnackBarWithoutAction(stringId: Int) {
        Snackbar.make(binding.root, getString(stringId), Snackbar.LENGTH_LONG).show()
    }

}