package com.example.sunapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunapp.model.*

class WeatherViewModel{

    val weatherModel = MutableLiveData<WeatherResponse>()

    fun getWeather() {
//        val
//        weatherModel.postValue(currentQuote)
    }
}

/*lass QuoteViewModel : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel>()

    fun randomQuote() {
        val currentQuote = QuoteProvider.random()
        quoteModel.postValue(currentQuote)
    }
}*/