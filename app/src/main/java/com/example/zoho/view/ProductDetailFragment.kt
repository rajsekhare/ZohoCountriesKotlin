package com.example.zoho.view
import android.os.Bundle

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.Volley

import com.example.zoho.R
import com.example.zoho.model.Countries
import com.example.zoho.viewmodel.ByteRequest
import com.pixplicity.sharp.Sharp
import kotlinx.android.synthetic.main.country_item.view.*
import kotlinx.android.synthetic.main.fragment_vehicle.*
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.Exception




class ProductDetailFragment : Fragment() {

    lateinit var product: Countries

    companion object {
        const val KEY_PRODUCT = "KEY_PRODUCT"

        fun newInstance(countries: Countries): ProductDetailFragment {
            val args = Bundle()
            args.putSerializable(KEY_PRODUCT, countries)
            val fragment = ProductDetailFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        arguments?.let { product = it.getSerializable(KEY_PRODUCT) as Countries }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainActivity = activity as MainActivity?
        mainActivity?.fragmentOne=true;
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear()
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            (activity as AppCompatActivity).supportActionBar?.title = "Country Detail"
            val flag = view?.findViewById<ImageView>(R.id.imageView)
        val name = view?.findViewById<TextView>(R.id.name)
        val capital = view?.findViewById<TextView>(R.id.capital)
        val region = view?.findViewById<TextView>(R.id.region)
        val subregion = view?.findViewById<TextView>(R.id.subregion)
        val population = view?.findViewById<TextView>(R.id.population)
        val area = view?.findViewById<TextView>(R.id.area)
        val currencies = view?.findViewById<TextView>(R.id.currency)
        val languages = view?.findViewById<TextView>(R.id.language)
        val language=""
        val sb = StringBuilder()


       if(product.name==null||product.name==""){
           name?.text="Name: Not Available"

       }else{
           name?.text="Name: "+product.name
       }
        if(product.capital==null||product.capital==""){
            capital?.text="Capital: Not Available"

        }else{
            capital?.text="Capital: "+product.capital
        }
        if(product.region==null||product.region==""){
            region?.text="Region: Not Available"

        }else{
            region?.text="Region: "+product.region
        }
        if(product.subregion==null||product.subregion==""){
            subregion?.text="Sub Region: Not Available"

        }else{
            subregion?.text="Sub Region: "+product.subregion
        }
        if(product.population==null||product.population.toString()==""){
            population?.text="Population: Not Available"

        }else{
            population?.text="Population: "+product.population
        }
        if(product.area==null||product.area.toString()==""){
            area?.text="Area: Not Available"

        }else{
            area?.text="Area: "+product.area+" Sq.Km"
        }

        val request= ByteRequest(product.flag, Response.Listener {
            val stream: InputStream = ByteArrayInputStream(it)
            try {
                Sharp.loadInputStream(stream).into(flag)
            }
            catch (e: Exception){
                e.stackTrace
            }
            stream.close()
        }, Response.ErrorListener {
            flag.setImageResource(R.drawable.ic_launcher_foreground)
        })
        Volley.newRequestQueue(context).add(request)
    }
}