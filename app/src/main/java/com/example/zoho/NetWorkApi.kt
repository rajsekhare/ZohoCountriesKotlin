package com.example.zoho


import com.example.zoho.model.Countries
import retrofit2.Call
import retrofit2.http.GET

interface NetWorkApi{

    @GET("all")
    fun getCountries(): Call<List<Countries>>

}