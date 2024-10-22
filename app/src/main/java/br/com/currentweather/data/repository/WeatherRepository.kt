package br.com.currentweather.data.repository

import br.com.currentweather.R
import br.com.currentweather.commom.util.testing.EspressoIdlingResourceHelper
import br.com.currentweather.commom.util.testing.IdlingResourceHelper
import br.com.currentweather.data.api.models.ForecastDailyResponse
import br.com.currentweather.data.api.models.WeatherResponse
import br.com.currentweather.data.models.WeatherData
import br.com.currentweather.data.network.CurrentWeatherSource

class WeatherRepository(
    private val currentWeatherSource: CurrentWeatherSource = CurrentWeatherSource(),
    private val mIdlingResourceHelper: IdlingResourceHelper = EspressoIdlingResourceHelper()
) {

    suspend fun getWeatherData(name: String): WeatherData<WeatherResponse> {
        try {
            mIdlingResourceHelper.increment()
            val weatherData = currentWeatherSource.fetchWeatherData(name)
            if (weatherData != null) {
                return WeatherData(true, "", weatherData)
            }
        } catch (e: Exception) {
            return WeatherData(false, e.message.orEmpty(), null)
        } finally {
            mIdlingResourceHelper.decrement()
        }

        return WeatherData(false, R.string.error_message.toString(), null)
    }

    suspend fun getWeatherData5d(name: String): WeatherData<ForecastDailyResponse> {
        try {
            mIdlingResourceHelper.increment()
            val weatherData5d = currentWeatherSource.fetchWeatherData5d(name)
            if (weatherData5d != null) {
                return WeatherData(true, "", weatherData5d)
            }
        } catch (e: Exception) {
            return WeatherData(false, e.message.orEmpty(), null)
        } finally {
            mIdlingResourceHelper.decrement()
        }
        return WeatherData(false, R.string.error_message.toString(), null)
    }
}
