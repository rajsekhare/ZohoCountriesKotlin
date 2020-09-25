package com.example.zoho.view

import android.content.ClipData
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zoho.R
import com.example.zoho.model.Countries
import com.example.zoho.viewmodel.CountryViewHolder


class CountryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfCountries: ArrayList<Countries> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false))
    }

    override fun getItemCount(): Int = listOfCountries.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = viewHolder as CountryViewHolder
        movieViewHolder.bindView(listOfCountries[position])
    }

    fun setMovieList(listOfCountries: ArrayList<Countries>) {
        this.listOfCountries = listOfCountries
        notifyDataSetChanged()
    }
    fun setFilter(newsArrayList: ArrayList<Countries>) {
        listOfCountries.clear()
        listOfCountries.addAll(newsArrayList)
        notifyDataSetChanged()
    }

}


