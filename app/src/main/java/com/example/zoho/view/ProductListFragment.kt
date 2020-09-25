package com.example.zoho.view


import android.annotation.SuppressLint
import android.database.Observable
import android.opengl.Visibility
import android.os.Bundle

import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager



import com.example.zoho.R
import com.example.zoho.model.Countries
import com.example.zoho.viewmodel.ProductViewModel

import kotlinx.android.synthetic.main.fragment_vehicle.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.fragment.app.FragmentActivity
import android.app.Activity
import com.example.zoho.database.AppDatabase
import com.example.zoho.repository.CountryDao
import android.text.method.TextKeyListener.clear
import android.util.Log

import android.view.*
import android.widget.SearchView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.zoho.Utils.Utils
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import okhttp3.internal.Util
import java.util.ArrayList


class ProductListFragment : Fragment() {
    var inSearch:Boolean = false
    lateinit var searchList : List<Countries>
    companion object {
        lateinit var myContext: FragmentActivity

        fun newInstance() = ProductListFragment()
    }
    lateinit var countryDao:CountryDao
    private val productListModel: ProductViewModel by viewModel()
    lateinit var countryDetail: List<Countries>
    lateinit  var progress:ProgressBar
    private var db: AppDatabase? = null
    val countryAdapter = CountryAdapter()
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
        recyclerView = rootView?.findViewById<RecyclerView>(R.id.recyclerView)
        progress = rootView?.findViewById<ProgressBar>(R.id.countryProgress)
        progress?.visibility = View.VISIBLE
        countryDao = AppDatabase.getAppDataBase(myContext)!!.countryDao()
        recyclerView.adapter = countryAdapter

        if (Utils.isNetworkAvailable(myContext)){
            productListModel.getProducts()
        }else{
            Toast.makeText(myContext,"No Internet", Toast.LENGTH_LONG).show()

        }
        productListModel.listOfProducts.observe(
            myContext,
            Observer(function = fun(productList: List<Countries>?) {
                productList?.let {
                    val manager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
                    manager?.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                    recyclerView.setLayoutManager(manager)
                    recyclerView.setItemAnimator(null);
                    recyclerView.addItemDecoration(GridItemDecoration(10, 2))
                    countryDetail = productList
                    var arrayList = arrayListOf<Countries>()
                    for (element in productList) {
                        arrayList.add(element)
                    }
                    countryAdapter.setMovieList(arrayList)
                    progress?.visibility = View.GONE


                }
            })

        )


        recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(myContext, recyclerView, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                //do your work here..
                if(searchList.size!=0){
                    var countrynametoBeSearched:String=searchList.get(position).name
                    lateinit var clickedElement:Countries
                    for(element in countryDetail){

                        if(element.name==countrynametoBeSearched){
                            clickedElement=element
                        }
                    }
                    val newFragment = ProductDetailFragment.newInstance(clickedElement)
                    val transaction = fragmentManager!!.beginTransaction()
                    transaction.replace(R.id.frag_container, newFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
                else{
                    val productListFragment = ProductDetailFragment.newInstance(countryDetail.get(position))
                    val manager = myContext.supportFragmentManager
                    val transaction =  manager.beginTransaction()
                    transaction.add(R.id.frag_container, productListFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }


            }
            override fun onItemLongClick(view: View?, position: Int) {
                System.out.println("item long click")
            }
        }))

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Countries"

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inSearch=false
    }
    @SuppressLint("WrongConstant")
    override fun onStart() {
        super.onStart()



    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.country, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(onQueryTextListener)
        searchView.setOnClickListener(View.OnClickListener {
        }
        )
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            getCountriesFromDb(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            getCountriesFromDb(newText)
            return true
        }

        private fun getCountriesFromDb(searchText: String) {
            var searchText = searchText
            searchText = "%$searchText%"
            System.out.println("the text is "+searchText)

            if (countryDao != null) {
                countryDao.getCountryByName(searchText)
                    .observe(myContext, object : Observer<List<Countries>> {
                        override fun onChanged(@Nullable deals: List<Countries>) {
                            searchList=deals
                            if (deals.size == 0) {
                                inSearch=false

                                var arrayList = arrayListOf<Countries>()
                                for(element in countryDetail){
                                    arrayList.add(element)
                                }
                                countryAdapter.setFilter(arrayList)

                            } else {

                                var arrayList = arrayListOf<Countries>()
                                for(element in deals){
                                    arrayList.add(element)
                                }
                                countryAdapter.setFilter(arrayList)
                            }

                        }
                    })

            }
        }
    }
    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    override fun onResume() {
        super.onResume()
        inSearch=false
        searchList= listOf()

    }

    override fun onPause() {
        super.onPause()
        inSearch=false
        searchList= listOf()

    }
}
