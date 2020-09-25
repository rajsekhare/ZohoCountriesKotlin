package com.example.zoho.model.WeatherModel


class MainWeather {

    var current: Current? = null

    var timezone: String? = null

    var timezone_offset: String? = null

    var daily: ArrayList<Daily>? = null

    var lon: String? = null


    var lat: String? = null

    override fun toString(): String {
        return "ClassPojo [current = $current, timezone = $timezone, timezone_offset = $timezone_offset, daily = $daily, lon = $lon, lat = $lat]"
    }
}
