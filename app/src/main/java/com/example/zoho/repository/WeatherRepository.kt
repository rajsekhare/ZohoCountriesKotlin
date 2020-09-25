package com.example.zoho.repository

import com.example.zoho.NetWorkApi
import com.example.zoho.Utils.Constants
import com.example.zoho.WeatherApi
import com.example.zoho.model.Countries
import com.example.zoho.model.WeatherModel.MainWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(val weatherApi: WeatherApi) {

    fun getWeather(OnWeathertData: OnWeathertData) {

        weatherApi.getWeather(Constants.cityLat, Constants.cityLon, Constants.key).enqueue(object:
            Callback<MainWeather> {
            override fun onFailure(call: Call<MainWeather>, t: Throwable) {
                OnWeathertData.onFailure()
            }

            override fun onResponse(
                call: Call<MainWeather>,
                response: Response<MainWeather>
            ) {
                OnWeathertData.onSuccess((response.body() as MainWeather))
            }

        })
    }
    interface OnProductData {
        fun onSuccess(data: List<Countries>)
        fun onFailure()
    }
    interface OnWeathertData {
        fun onSuccess(data: MainWeather)
        fun onFailure()
    }
}

