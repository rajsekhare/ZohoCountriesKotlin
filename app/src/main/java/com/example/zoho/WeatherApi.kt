package com.example.zoho

import com.example.zoho.model.WeatherModel.MainWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi{

    @GET("onecall")
    fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") api: String): Call<MainWeather>

}