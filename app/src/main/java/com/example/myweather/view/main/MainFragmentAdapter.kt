package com.example.myweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.domain.Weather
import com.example.myweather.view.OnItemViewClickListener
import ru.geekbrains.lesson_1423_2_2_main.R

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainFragmentViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    private lateinit var listener : OnItemViewClickListener

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged() // Обновление данных
    }
    fun setOnItemViewClickListener(onItemViewClickListener: OnItemViewClickListener){
        listener = onItemViewClickListener
    }

    // создаем связь между холдером и идентификатором макета
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val holder = MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
        )
        return holder
    }

    // позиционирование на конкретном элементе списка
    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.renderData(weatherData[position])
    }

    // тут получаем количество элементов в списке
    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun renderData(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.name
            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "work it", Toast.LENGTH_LONG).show()
                listener.onItemClick(weather)
            }
        }
    }

}