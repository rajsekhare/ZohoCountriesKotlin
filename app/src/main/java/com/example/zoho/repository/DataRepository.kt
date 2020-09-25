package com.example.zoho.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.zoho.NetWorkApi
import com.example.zoho.Utils.Constants
import com.example.zoho.database.AppDatabase
import com.example.zoho.model.Countries
import com.example.zoho.model.WeatherModel.MainWeather
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(val netWorkApi: NetWorkApi) {

    fun getProducts(onProductData: OnProductData) {

        netWorkApi.getCountries().enqueue(object: Callback<List<Countries>> {
            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                //handle error here
                onProductData.onFailure()
            }

            override fun onResponse(
                call: Call<List<Countries>>,
                response: Response<List<Countries>>
            ) {
                onProductData.onSuccess((response.body() as List<Countries>))
            }

        })


    }
    interface OnProductData {
        fun onSuccess(data: List<Countries>)
        fun onFailure()
    }

}

