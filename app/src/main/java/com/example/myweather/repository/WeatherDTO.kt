package com.example.myweather.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO (val fact : FactDTO):Parcelable