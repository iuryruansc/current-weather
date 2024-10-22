package br.com.currentweather.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.currentweather.data.api.models.ForecastDailyResponse
import br.com.currentweather.data.api.models.WeatherResponse
import br.com.currentweather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private var _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: MutableStateFlow<WeatherResponse?> get() = _weather

    private var _weather5d = MutableStateFlow<ForecastDailyResponse?>(null)
    val weather5d: MutableStateFlow<ForecastDailyResponse?> get() = _weather5d

    private var _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    private var _isErrorOccurred = MutableStateFlow(false)
    val isErrorOccurred: MutableStateFlow<Boolean> get() = _isErrorOccurred

    suspend fun weatherData(name: String) {
        val response = weatherRepository.getWeatherData(name)
        if (response.success) {
            val weather = response.data
            _weather.value = weather
        } else {
            _isErrorOccurred.value = true
            _errorMessage.value = response.message
        }
    }

    suspend fun weatherData5d(name: String) {
        val response = weatherRepository.getWeatherData5d(name)
        if (response.success) {
            val weather5d = response.data
            _weather5d.value = weather5d
        } else {
            _isErrorOccurred.value = true
            _errorMessage.value = response.message
        }
    }
}
