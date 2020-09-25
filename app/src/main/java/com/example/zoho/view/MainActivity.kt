package com.example.zoho.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

import com.example.zoho.R

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.zoho.Utils.Constants
import com.example.zoho.Utils.GPSUtils
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import androidx.core.app.ActivityCompat.requestPermissions
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.zoho.Utils.Utils
import com.example.zoho.model.Countries
import okhttp3.internal.Util
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess
import com.example.zoho.view.ProductDetailFragment.Companion as ProductDetailFragment1


class MainActivity : AppCompatActivity()  {

    var fragmentOne:Boolean?=null
    lateinit var countryDetail: Countries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fragmentOne=true
        if(checkLocationPermission()){
            GPSUtils.instance.findDeviceLocation(this@MainActivity)
            Constants?.cityLat= GPSUtils.instance.latitude.toString()
            Constants?.cityLon= GPSUtils.instance.longitude.toString()
            System.out.println("the latitude and longtitude raw is "+GPSUtils.instance.latitude.toString()+" "+GPSUtils.instance.longitude.toString())
            System.out.println("the latitude and longtitude assigned is "+Constants?.cityLat+" "+Constants?.cityLon)

            if(Utils.isNetworkAvailable(this)) {

                val geocoder = Geocoder(this, Locale.getDefault())
                var addresses: List<Address>? = null
                try {
                    addresses = geocoder.getFromLocation(
                        (Constants?.cityLat).toDouble(),
                        (Constants?.cityLon).toDouble(),
                        1
                    )
                } catch (e: IOException) {
                    e.printStackTrace()


                }

                val address = addresses!![0].subLocality
                val cityName = addresses[0].locality
                val stateName = addresses[0].adminArea
                System.out.println("the latitude and longtitude is " + address + " " + cityName)
                Constants?.cityName = address
            }
            else{
                Toast.makeText(this,"No Internet", Toast.LENGTH_LONG).show()

            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, ProductListFragment()).commit()

        }
        else{



        }

    }

    override fun onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
//        locationManager.requestLocationUpdates(provider, 400, 1, this);

        }
    }

    override fun onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
//        locationManager.removeUpdates(this);
        }
    }
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)

                System.out.println("inside if")
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                System.out.println("inside else")

            }
            return false

        }else{
            return true;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.country, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val weather = menu.findItem(R.id.weather)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.weather) {

            val weatherFragment = WeatherFragment.newInstance()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.add(R.id.frag_container, weatherFragment)
            transaction.addToBackStack(null)
            transaction.commit()
//
            return true
        }

        return super.onOptionsItemSelected(item)

    }
    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(bundle:Bundle) {
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        GPSUtils.instance.findDeviceLocation(this@MainActivity)
                        Constants?.cityLat= GPSUtils.instance.latitude.toString()
                        Constants?.cityLon= GPSUtils.instance.longitude.toString()
                        System.out.println("the latitude and longtitude raw is "+GPSUtils.instance.latitude.toString()+" "+GPSUtils.instance.longitude.toString())
                        System.out.println("the latitude and longtitude assigned is "+Constants?.cityLat+" "+Constants?.cityLon)

                        if(Utils.isNetworkAvailable(this)){
                            val geocoder = Geocoder(this, Locale.getDefault())
                            var addresses: List<Address>? = null
                            try {
                                addresses = geocoder.getFromLocation((Constants?.cityLat).toDouble(), (Constants?.cityLon).toDouble(), 1)
                            } catch (e: IOException) {
                                e.printStackTrace()

                            }
                            val address = addresses!![0].subLocality
                            val cityName = addresses[0].locality
                            val stateName = addresses[0].adminArea
                            System.out.println("the latitude and longtitude is "+address+" "+cityName)
                            Constants?.cityName=address
                        }
                        else{
                            Toast.makeText(this,"No Internet", Toast.LENGTH_LONG).show()

                        }

                        val productListFragment = ProductListFragment.newInstance()
                        val manager = supportFragmentManager
                        val transaction = manager.beginTransaction()
                        transaction.add(R.id.frag_container, productListFragment)
                        transaction.addToBackStack(null)
                        transaction.commitAllowingStateLoss()



                    }
                } else {
                    Toast.makeText(this, "Please enable the location to use the app", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}
