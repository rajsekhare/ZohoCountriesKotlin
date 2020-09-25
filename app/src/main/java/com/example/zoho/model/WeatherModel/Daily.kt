package com.example.zoho.model.WeatherModel

class Daily {
//    var rain: List<Rain>? = null

    var sunrise: String? = null

    var temp: Temp? = null

    var uvi: String? = null

    var pressure: String? = null

    var clouds: String? = null

    var feels_like:  Feels_Like? = null

    var dt: String? = null

    var pop: String? = null

    var wind_deg: String? = null

    var dew_point: String? = null

    var sunset: String? = null

    var weather: Array<Weather>? = null

    var humidity: String? = null

    var wind_speed: String? = null

    override fun toString(): String {
        return "ClassPojo [ sunrise = $sunrise, temp = $temp, uvi = $uvi, pressure = $pressure, clouds = $clouds, feels_like = $feels_like, dt = $dt, pop = $pop, wind_deg = $wind_deg, dew_point = $dew_point, sunset = $sunset, weather = $weather, humidity = $humidity, wind_speed = $wind_speed]"
    }
}
