package com.example.zoho

import android.app.Application
import com.example.zoho.repository.DataRepository
import com.example.zoho.repository.WeatherRepository
import com.example.zoho.view.WeatherViewModel
import com.example.zoho.viewmodel.ProductViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single { DataRepository(get()) }
    single { WeatherRepository(get()) }
    single { createWebService() }
    single { createWebServiceWeather() }
    viewModel { ProductViewModel(get()) }
    viewModel { WeatherViewModel(get()) }

}

fun createWebService(): NetWorkApi {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.eu/rest/v2/")
        .client(client)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    return retrofit.create(NetWorkApi::class.java)
}

fun createWebServiceWeather(): WeatherApi {

    val gson = GsonBuilder()
        .setLenient()
        .create()
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(client)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    return retrofit.create(WeatherApi::class.java)
}