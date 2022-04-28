package com.example.sunapp.model

data class WeatherResponse(
    var cod: String,
    var city: City,
    var listDays: List<DayWeather>
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
    var temp: String,
    var pressure: String
)

data class Weather(
    var icon: String,
    var main: String,
    var description: String
)
