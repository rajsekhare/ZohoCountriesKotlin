package com.example.zoho.Utils

import android.Manifest
import android.R.string.cancel
import android.content.DialogInterface
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import android.app.Activity
import android.widget.Toast
import android.location.LocationManager
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService


class GPSUtils() {
    private var locationManager: LocationManager? = null
    var latitude: String? = null
    var longitude: String? = null

    fun initPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )

    }

    fun findDeviceLocation(activity: Activity) {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //Check gps is enable or not
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps
            gpsEnable(activity)
        } else {
            //GPS is already On then
            getLocation(activity)
        }
    }

    private fun getLocation(activity: Activity) {

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,

                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )

        } else {
            val LocationGps = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val LocationNetwork =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val LocationPassive =
                locationManager!!.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            if (LocationGps != null) {
                val lat = LocationGps.latitude
                val longi = LocationGps.longitude

                latitude = lat.toString()
                longitude = longi.toString()

            } else if (LocationNetwork != null) {
                val lat = LocationNetwork.latitude
                val longi = LocationNetwork.longitude

                latitude = lat.toString()
                longitude = longi.toString()

            } else if (LocationPassive != null) {
                val lat = LocationPassive.latitude
                val longi = LocationPassive.longitude

                latitude = lat.toString()
                longitude = longi.toString()

            } else {
                Toast.makeText(activity, "Can't Get Your Location", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun gpsEnable(activity: Activity) {

        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES",
            DialogInterface.OnClickListener { dialog, which ->
                activity.startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }).setNegativeButton("NO",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {

        private val REQUEST_LOCATION = 1
        val instance = GPSUtils()
    }


}