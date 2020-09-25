package com.example.zoho.model.WeatherModel

class Temp {
    var min: String? = null

    var max: String? = null

    var eve: String? = null

    var night: String? = null

    var day: String? = null

    var morn: String? = null

    override fun toString(): String {
        return "ClassPojo [min = $min, max = $max, eve = $eve, night = $night, day = $day, morn = $morn]"
    }
}
