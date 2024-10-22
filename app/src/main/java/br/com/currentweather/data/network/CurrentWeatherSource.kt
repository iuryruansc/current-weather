package br.com.currentweather.data.network

import br.com.currentweather.data.api.ApiService
import br.com.currentweather.data.api.ApiServiceClient
import br.com.currentweather.data.api.models.ForecastDailyResponse
import br.com.currentweather.data.api.models.WeatherResponse

class CurrentWeatherSource(private val apiService: ApiService = ApiServiceClient.instance) {

    suspend fun fetchWeatherData(name: String): WeatherResponse? {
        val weatherResponse = apiService.getWeather(name)
        return weatherResponse.body()
    }

    suspend fun fetchWeatherData5d(name: String): ForecastDailyResponse? {
        val weatherResponse = apiService.getWeather5d(name)
        return weatherResponse.body()
    }
}
