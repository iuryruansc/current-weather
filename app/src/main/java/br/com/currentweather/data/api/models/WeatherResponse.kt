package br.com.currentweather.data.api.models

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)
