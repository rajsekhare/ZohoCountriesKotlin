package com.example.zoho.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.FileUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode

import com.example.zoho.R
import com.example.zoho.Utils.Constants
import com.example.zoho.Utils.GPSUtils
import com.example.zoho.Utils.Utils
import com.example.zoho.model.Countries
import com.example.zoho.model.WeatherModel.Daily
import com.example.zoho.model.WeatherModel.MainWeather
import com.example.zoho.viewmodel.ProductViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class WeatherFragment : Fragment(){
//    val anyChartView :AnyChartView = null

    companion object {
        fun newInstance() = WeatherFragment()
    }

    var thiscontext: Context? = null
    private val weatherViewModel: WeatherViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        setHasOptionsMenu(true);
        val mainActivity = activity as MainActivity?
        mainActivity?.fragmentOne=false;
        thiscontext = container?.getContext();
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Weather"

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        super.setHasOptionsMenu(true);
    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.weather, menu);
//        menu.clear();
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return (when(item.itemId) {
//
//            else ->
//                super.onOptionsItemSelected(item)
//        })
//    }

    override fun onPrepareOptionsMenu(menu:Menu) {
    super.onPrepareOptionsMenu(menu);

        menu.clear();

    }

    @SuppressLint("WrongConstant")
    override fun onStart() {
        super.onStart()
        val tempText = view?.findViewById<TextView>(R.id.tempText)
        val condition = view?.findViewById<TextView>(R.id.condition)
        val cityText = view?.findViewById<TextView>(R.id.cityText)
        val imageWeather = view?.findViewById<ImageView>(R.id.imageWeather)
        val anyChartView = view?.findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView?.setProgressBar(view?.findViewById<ProgressBar>(R.id.progress_bar));
        val recyclerView : RecyclerView? =view?.findViewById<RecyclerView>(R.id.recyclerviewWeather)
        val daywise : TextView? =view?.findViewById<TextView>(R.id.daywise)

        anyChartView?.visibility = View.GONE
        daywise?.visibility = View.GONE
        if (Utils.isNetworkAvailable(thiscontext)){
        weatherViewModel.getWeather()}
        else{
            Toast.makeText(thiscontext,"No Internet", Toast.LENGTH_LONG).show()

        }
        weatherViewModel.weatherList.observe(this, Observer(function = fun(weatherList: MainWeather?) {
            weatherList?.let {
                //
                System.out.println("the weather list is "+weatherList)
                tempText?.text=
                    (weatherList?.current?.temp?.let { it1 -> Utils.getCelciusFromKelvin(it1) });
                condition?.text=weatherList?.current?.weather?.get(0)?.description
//                cityText?.text=weatherList?.timezone


                Picasso.with(thiscontext)
                    .load("http://openweathermap.org/img/w/" +weatherList?.current?.weather?.get(0)?.icon+".png")
                    .into(imageWeather)
                val data = weatherList.daily
                anyChartView?.let { it1 -> data?.let { it2 -> setGraph(it1, it2) } }
                val forecastData = Arrays.asList(weatherList.daily)
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = data?.let { it1 -> WeatherAdapter(it1) }
                }
                cityText?.text=Constants.cityName
                daywise?.visibility = View.VISIBLE

            }
        }))
    }
    fun setGraph(anyChart:AnyChartView,dailyData:ArrayList<Daily>) {

        val cartesian = AnyChart.column()

        val data = ArrayList<DataEntry>()
        for (i in dailyData.indices) {

               val num3=Utils.getCelciusFromKelvin((dailyData?.get(i)?.temp?.max).toString())?.toDouble()

            data.add(
                ValueDataEntry(
                    dailyData?.get(i)?.dt?.let { Utils.changeDateformat(it) },
                    num3
                )
            )
        }
        val column = cartesian.column(data)

        column.color("#2c3e50")
        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}{groupsSeparator: } °c")

        cartesian.animation(true)
        cartesian.title("Weather report for coming days")
        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } °c")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)
        cartesian.xAxis(0).title("Date")
        cartesian.yAxis(0).title("Temprature")
        anyChart.setChart(cartesian)
        anyChart.setVisibility(View.VISIBLE)
    }

}
