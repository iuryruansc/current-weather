package br.com.currentweather.data.api.models

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: Int
)
