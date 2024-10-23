package br.com.currentweather

import br.com.currentweather.data.api.models.Main
import br.com.currentweather.data.api.models.Weather
import br.com.currentweather.data.api.models.WeatherResponse
import br.com.currentweather.data.models.WeatherData

object WeatherTestMock {

    val weatherDataMockSuccess = WeatherResponse(
        weather = listOf(Weather(description = "few clouds", icon = 2, id = 801, main = "Clouds")),
        main = Main(temp = 35.84, feelsLike = 36.96, tempMin = 35.84, tempMax = 35.84, humidity = 74),
        name = "Teresina"
    )

    val weatherResponseMockExist = WeatherData(
        success = true,
        message = "",
        data = weatherDataMockSuccess
    )

    val weatherResponseMockNotExist = WeatherData(
        success = false,
        message = "2131689528",
        data = null
    )

    val weatherResponseMockException = WeatherData(
        success = false,
        message = "Error Message",
        data = null
    )
}
