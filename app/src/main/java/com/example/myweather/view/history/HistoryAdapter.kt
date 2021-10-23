package com.example.myweather.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.R
import com.example.myweather.domain.Weather
import com.example.myweather.view.OnItemClearListener

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    private lateinit var listener:OnItemClearListener;

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged() // Обновление данных
    }

    fun getWeather():List<Weather>{
        return weatherData
    }

    fun clearWeather(){
        listener.clear(weatherData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val holder = HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_recyclerview_item, parent, false)
        )
        return holder
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.renderData(weatherData[position])
    }

    // тут получаем количество элементов в списке
    override fun getItemCount(): Int {
        return weatherData.size
    }


    inner class HistoryViewHolder(view : View):RecyclerView.ViewHolder(view){
        fun renderData(weather: Weather) {
            itemView.findViewById<TextView>(R.id.recyclerViewItem).text =
                weather.city.name
            }
        }
    }