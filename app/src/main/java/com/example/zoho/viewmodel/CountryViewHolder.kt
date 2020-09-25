package com.example.zoho.viewmodel


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.zoho.R
import com.example.zoho.model.Countries
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.country_item.view.*
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.Exception
import java.util.*

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mRandom = Random()
    fun bindView(countries: Countries) {
        itemView.name.text = countries.name
        itemView.imageCountry.layoutParams.height = getRandomIntInRange(250, 150)
        itemView.setOnClickListener {

        }

        val request= ByteRequest(countries.flag, Response.Listener {
            val stream: InputStream = ByteArrayInputStream(it)
            try {
                Sharp.loadInputStream(stream).into(itemView.imageCountry)
            }
            catch (e:Exception){
            e.stackTrace
            }
            stream.close()
        }, Response.ErrorListener {
            itemView.imageCountry.setImageResource(R.drawable.ic_launcher_foreground)
        })
        Volley.newRequestQueue(itemView.context).add(request)
//        Glide.with(itemView.context).load(countries.flag).into(itemView.image)
    }

    private fun getRandomIntInRange(max: Int, min: Int): Int {
        return mRandom.nextInt(max - min + min) + min
    }

}