package com.example.sunapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _weatherModel = MutableLiveData<WeatherResponse>()
    val weatherModel: LiveData<WeatherResponse> get() = _weatherModel

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val service = WeatherApi.initRetrofit()


    private val _weatherRequest: RequestWeather
        get() = weatherRequest
    private val weatherRequest: RequestWeather get() = _weatherRequest

    fun getWeather(requestWeather: RequestWeather) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                service.getWeather(
                    "${requestWeather.zip},${requestWeather.country}",
                    "abca655c8fb6c771b90146dd2e747976",
                    requestWeather.gradeType.nameGrade
                ).enqueue(
                    object : Callback<WeatherResponse> {
                        override fun onResponse(
                            call: Call<WeatherResponse>,
                            response: Response<WeatherResponse>
                        ) {
                            Log.d("getWeather", "onResponse: ${response.raw()}")
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    _weatherModel.value = it
                                } ?: kotlin.run {
                                    _error.value = response.errorBody()?.string()
                                }
                            } else {
                                _error.value = response.errorBody()?.string()
                            }
                            _isLoading.value = false
                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                            _error.value = t.localizedMessage ?: "Unknown Error"
                            _isLoading.value = false
                        }

                    }
                )
            }
        }

    }

    suspend fun getWeatherC() {
        withContext(Dispatchers.IO) {
            val response = service.getWeatherCoroutine(
                "${weatherRequest.zip},${weatherRequest.country}",
                "abca655c8fb6c771b90146dd2e747976",
                weatherRequest.gradeType.nameGrade
            )
            response.body()!!
        }
    }
}
