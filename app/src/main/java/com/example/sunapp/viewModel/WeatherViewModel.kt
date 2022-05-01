package com.example.sunapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _weatherModel = MutableLiveData<WeatherResponse>()
    val weatherModel: LiveData<WeatherResponse> get() = _weatherModel

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val service = WeatherService.initRetrofit()

    fun getWeather(requestWeather: RequestWeather) {
        service.getWeather(
            "${requestWeather.zip},${requestWeather.country}",
            "abca655c8fb6c771b90146dd2e747976",
            requestWeather.gradeType.nameGrade
        )
            .enqueue(
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
                                _error.value = response.message()
                            }
                        } else {
                            _error.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        _error.value = t.localizedMessage ?: "Unknown Error"
                    }
                }
            )
    }


}
