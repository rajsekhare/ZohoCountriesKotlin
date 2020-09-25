package com.example.zoho.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.zoho.R
import com.example.zoho.Utils.Utils
import com.example.zoho.model.WeatherModel.Daily
import com.example.zoho.viewmodel.WeatherViewHolder
import java.util.ArrayList

class WeatherAdapter(private val list: ArrayList<Daily>)
    : RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeatherViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val daily: Daily = list[position]
        holder.bind(daily)
    }

    override fun getItemCount(): Int = list.size

}