package br.com.currentweather.data.models

data class WeatherData<T>(
    var success: Boolean,
    var message: String?,
    var data: T? = null
)
