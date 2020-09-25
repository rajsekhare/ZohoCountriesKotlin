package com.example.zoho.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zoho.NetWorkApi
import com.example.zoho.WeatherApi
import com.example.zoho.model.WeatherModel.MainWeather
import com.example.zoho.repository.DataRepository
import com.example.zoho.repository.WeatherRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.standalone.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel (val weatherRepository: WeatherRepository) : ViewModel(), KoinComponent {
    var weatherList = MutableLiveData<MainWeather>()

    init {

        weatherList.value = MainWeather()

    }

    fun getWeather() {
        weatherRepository.getWeather(object : WeatherRepository.OnWeathertData {
            override fun onSuccess(data: MainWeather) {
                weatherList.value = data
            }

            override fun onFailure() {
                //REQUEST FAILED

            }
        })
    }

}
