package com.example.zoho.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zoho.R
import com.example.zoho.Utils.Utils
import com.example.zoho.model.WeatherModel.Daily
import com.squareup.picasso.Picasso

class WeatherViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.forecast_item, parent, false)) {
    private var date: TextView? = null
    private var temp: TextView? = null
    private var detail: TextView? = null
    private var image: ImageView? = null
    private var context:Context=parent.context


    init {
        temp = itemView.findViewById(R.id.temp)
        date = itemView.findViewById(R.id.dateText)
        detail = itemView.findViewById(R.id.detail)
        image = itemView.findViewById(R.id.imageForecast)
    }

    fun bind(daily: Daily) {


        date?.text= daily?.dt?.let { Utils.changeDateformat(it) }
        temp?.text= (daily?.temp?.max.let { it1 -> it1?.let { Utils.getCelciusFromKelvin(it) } })+"°c";
        detail?.text=daily?.weather?.get(0)?.description
        Picasso.with(context)
            .load("http://openweathermap.org/img/w/" +daily?.weather?.get(0)?.icon+".png")
            .into(image)

    }

}