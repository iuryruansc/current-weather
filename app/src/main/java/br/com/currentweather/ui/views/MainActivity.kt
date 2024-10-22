package br.com.currentweather.ui.views

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.currentweather.R
import br.com.currentweather.commom.util.ui.showErrorDialog
import br.com.currentweather.databinding.ActivityMainBinding
import br.com.currentweather.ui.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.cityName.windowToken, 0)

            val text = binding.textField.text.toString()
            if (text.isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.weatherData(text)
                    // viewModel.weatherData5d(text) Not available for free plan
                }
            } else {
                showErrorDialog(this@MainActivity, "Please enter a valid city name")
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeWeather() }
                launch { observeErrorMessage() }
                // launch { observeDailyForecast() } Not available for free plan
            }
        }
    }

    private suspend fun observeWeather() {
        viewModel.weather.collect { weather ->
            if (weather != null) {
                binding.apply {
                    cityName.text = weather.name
                    currentTemp.text = getString(R.string.temperature_celsius, weather.main.temp)
                    maxTempValue.text =
                        getString(R.string.temperature_celsius, weather.main.tempMax)
                    minTempValue.text =
                        getString(R.string.temperature_celsius, weather.main.tempMin)
                    feelsLikeValue.text =
                        getString(R.string.temperature_celsius, weather.main.feelsLike)
                    humidityValue.text =
                        getString(R.string.humidity_percentage, weather.main.humidity)
                    dataVisibility()
                }
            }
        }
    }

    // Not available for free plan
//    private suspend fun observeDailyForecast() {
//        viewModel.weather5d.collect { dailyForecast ->
//            if (dailyForecast != null) {
//                binding.apply {
//                    val dailyForecastTemps = listOf(
//                        forecastTemp1, forecastTemp2,
//                        forecastTemp3, forecastTemp4,
//                        forecastTemp5
//                    )
//                    val dailyForecastDates = listOf(
//                        forecastDate1, forecastDate2,
//                        forecastDate3, forecastDate4,
//                        forecastDate5
//                    )
//                    for (i in dailyForecastTemps.indices) {
//                        dailyForecastTemps[i].text =
//                            getString(R.string.temperature_celsius, dailyForecast.list[i].main.temp)
//                        dailyForecastDates[i].text = formattingDate(dailyForecast.list[i].dt)
//                    }
//                }
//            }
//        }
//    }

    private suspend fun observeErrorMessage() {
        viewModel.isErrorOccurred.collect { errorMessage ->
            if (errorMessage) {
                viewModel.errorMessage.value?.let { showErrorDialog(this, it) }
            }
        }
    }

    private fun dataVisibility() {
        binding.apply {
            cityName.visibility = View.VISIBLE
            currentTemp.visibility = View.VISIBLE
            maxTempValue.visibility = View.VISIBLE
            minTempValue.visibility = View.VISIBLE
            feelsLikeValue.visibility = View.VISIBLE
            humidityValue.visibility = View.VISIBLE
        }
    }

  //  function to format dt time for daily forecast
//    private fun formattingDate(dt: Long): String {
//        val date = Date(dt * 1000L)
//        val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
//        return dateFormat.format(date)
//    }
}
