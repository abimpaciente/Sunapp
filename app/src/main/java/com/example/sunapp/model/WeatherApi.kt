package com.example.sunapp.model

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(END_POINT_FORECAST)
    fun getWeather(
        @Query(PARAM_ZIP) zip: String,
        @Query(PARAM_APP_ID) appId: String,
        @Query(PARAM_UNITS) units: String,
    ): Call<WeatherResponse>


    @GET(END_POINT_FORECAST)
   suspend fun getWeatherCoroutine(
        @Query(PARAM_ZIP) zip: String,
        @Query(PARAM_APP_ID) appId: String,
        @Query(PARAM_UNITS) units: String,
    ): Response<WeatherResponse>

    companion object {
        fun initRetrofit(): WeatherApi {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }

    }


}