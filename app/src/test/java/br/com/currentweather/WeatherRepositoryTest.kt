package br.com.currentweather

import br.com.currentweather.commom.util.testing.MockIdlingResourceHelper
import br.com.currentweather.data.network.CurrentWeatherDataSource
import br.com.currentweather.data.repository.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    lateinit var mCurrentWeatherDataSourceMock: CurrentWeatherDataSource

    @Mock
    val mockIdlingResourceHelper = MockIdlingResourceHelper()

    @Test
    fun testIfGetWeatherDataIfCityExists() = runTest {
        `when`(mCurrentWeatherDataSourceMock.fetchWeatherData("Teresina")).thenReturn(WeatherTestMock.weatherDataMockSuccess)

        val expected = WeatherTestMock.weatherResponseMockExist

        val weatherRepository =
            WeatherRepository(mCurrentWeatherDataSourceMock, mockIdlingResourceHelper)

        val result = weatherRepository.getWeatherData("Teresina")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun testIfGetWeatherDataIfCityNotExists() = runTest {
        `when`(mCurrentWeatherDataSourceMock.fetchWeatherData("NotExist")).thenReturn(null)

        val expected = WeatherTestMock.weatherResponseMockNotExist

        val weatherRepository =
            WeatherRepository(mCurrentWeatherDataSourceMock, mockIdlingResourceHelper)

        val result = weatherRepository.getWeatherData("NotExist")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun testWhenGetWeatherReturnsException() = runTest {
        `when`(mCurrentWeatherDataSourceMock.fetchWeatherData("Teresina")).thenThrow(RuntimeException("Error Message"))

        val expected = WeatherTestMock.weatherResponseMockException

        val weatherRepository =
            WeatherRepository(mCurrentWeatherDataSourceMock, mockIdlingResourceHelper)

        val result = weatherRepository.getWeatherData("Teresina")

        Assert.assertEquals(expected, result)
    }
}
