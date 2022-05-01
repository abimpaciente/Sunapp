package com.example.sunapp.model

import com.example.sunapp.model.common.GradeType

data class WeatherResponse(
    var cod: String,
    var city: City,
    var list: List<DayWeather>,
    var message:String
)

data class City(
    var country: String,
    var name: String
)

data class DayWeather(
    var dt_txt: String,
    var main: Main,
    var weather: List<Weather>
)

data class Main(
    var temp: Double,
    var pressure: String
)

data class Weather(
    var icon: String,
    var main: String,
    var description: String
)

data class RequestWeather(
    var zip:String,
    var country: String,
    var gradeType: GradeType
)
