package com.example.sunapp.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(END_POINT_FORECAST)
    fun getWeather(
        @Query(PARAM_ZIP) zip: String,
        @Query(PARAM_APP_ID) appId: String,
        @Query(PARAM_UNITS) units: String,
    ): Call<WeatherResponse>

    companion object {
        fun initRetrofit(): WeatherService {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }

    }


}