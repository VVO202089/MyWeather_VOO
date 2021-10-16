package com.example.myweather.view

import com.example.myweather.domain.Weather

interface OnItemViewClickListener {
    fun onItemClick(weather: Weather)
}