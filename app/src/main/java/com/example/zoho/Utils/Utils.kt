package com.example.zoho.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val TAG = Utils::class.java.name

    /***
     * using this method to set the view visible
     */


    //    public static boolean isNetworkAvailable(Context context) {
    //        ConnectivityManager connectivityManager
    //                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    //        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    //        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    //    }


    fun changeDateformat(timestamp: String): String {

        val num = java.lang.Long.parseLong(timestamp)
        val sdf = SimpleDateFormat("dd MMMM")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        var finaldate = sdf.format(num * 1000)
        finaldate = finaldate.replace("September", "Sep")
        finaldate = finaldate.replace("October", "Oct")
        return finaldate.toString()
    }

    fun hideViews(vararg views: View) {
        for (view in views) {
            if (view == null) continue
            view.visibility = View.GONE
        }
    }

    /***
     * using this method to set the view visible
     * @param views list of views...
     */
    fun showViews(vararg views: View) {
        for (view in views) {
            if (view == null) continue
            view.visibility = View.VISIBLE
        }
    }


    /***
     * this method used to load image into specified imageView;
     * @param context pass context for Picasso to initiate;
     * @param imageView after loading the image will be appear on this widget;
     * @param url image url
     * @param errorImageDrawable in case error comes during fetching image from given url then this errorImageDrawable will be appear into imageView;
     */
    fun loadImage(
        context: Context,
        imageView: ImageView,
        url: Uri,
        placeHolder: Int,
        errorImageDrawable: Int
    ) {
        Picasso.with(context)
            .load(url)
            .placeholder(placeHolder)
            .error(errorImageDrawable)
            .into(imageView)

    }


    /***
     * @param context passing to get the network status
     * @return boolean value as true or false
     * true: if network is available
     * false: if not available
     */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Objects.requireNonNull(connectivityManager).activeNetworkInfo != null) {
            try {
                return connectivityManager.activeNetworkInfo!!.isConnected
            } catch (e: Exception) {
                return false
            }

        }
        return false
    }

    fun getCelciusFromKelvin(value: String): String {
        val df = DecimalFormat("00.0")
        val kelvin = java.lang.Float.parseFloat(value)
        // Kelvin to Degree Celsius Conversion
        val celsius = kelvin - 273.15f
        println("Celsius: $celsius")
        return df.format(celsius.toDouble()).toString()
    }


}
