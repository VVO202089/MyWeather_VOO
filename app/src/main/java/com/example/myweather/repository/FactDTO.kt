package com.example.myweather.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactDTO(

    val temp: Int,
    val feels_like: Int,
    val condition: String,
    val pressure_mm: Int,
    val wind_speed: Double,
    val icon: String

) : Parcelable