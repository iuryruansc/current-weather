package br.com.currentweather.data.api

import br.com.currentweather.data.api.models.ForecastDailyResponse
import br.com.currentweather.data.api.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appkey: String = "11212a90aeaf70b8b0beffff07df7eaa",
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>

    @GET("forecast/daily")
    suspend fun getWeather5d(
        @Query("q") city: String,
        @Query("appid") appkey: String = "11212a90aeaf70b8b0beffff07df7eaa",
        @Query("cnt") cnt: Int = 5,
        @Query("units") units: String = "metric"
    ): Response<ForecastDailyResponse>
}
