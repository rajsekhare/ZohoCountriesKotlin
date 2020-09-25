package com.example.zoho.model.WeatherModel

class Current {
    var rain: Rain? = null

    var sunrise: String? = null

    var temp: String? = null

    var visibility: String? = null

    var uvi: String? = null

    var pressure: String? = null

    var clouds: String? = null

    var feels_like: String? = null

    var dt: String? = null

    var wind_deg: String? = null

    var dew_point: String? = null

    var sunset: String? = null

    var weather: Array<Weather>? = null

    var humidity: String? = null

    var wind_speed: String? = null

    override fun toString(): String {
        return "ClassPojo [rain = $rain, sunrise = $sunrise, temp = $temp, visibility = $visibility, uvi = $uvi, pressure = $pressure, clouds = $clouds, feels_like = $feels_like, dt = $dt, wind_deg = $wind_deg, dew_point = $dew_point, sunset = $sunset, weather = $weather, humidity = $humidity, wind_speed = $wind_speed]"
    }
}
