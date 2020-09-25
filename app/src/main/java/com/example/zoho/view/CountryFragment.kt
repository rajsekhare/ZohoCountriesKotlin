package com.example.zoho.view

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.example.zoho.R
import androidx.core.content.ContextCompat
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.core.app.NotificationCompat.getCategory
import androidx.core.view.MenuItemCompat
import android.widget.SearchView
import androidx.annotation.Nullable
import com.android.volley.VolleyError
import retrofit2.http.GET
import com.android.volley.toolbox.StringRequest
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.zoho.database.AppDatabase
import com.example.zoho.model.Countries
import com.example.zoho.repository.CountryDao
import com.example.zoho.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class CountryFragment : Fragment() {
    lateinit var countryDao: CountryDao
    lateinit var countryDetail: ArrayList<Countries>
    lateinit var rvNewsArticle: RecyclerView
    var adapter: ProductListAdapter? = null
    private val productListModel: ProductViewModel by viewModel()
    companion object {
        lateinit var myContext: FragmentActivity
        fun newInstance() = ProductListFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)
        countryDao= AppDatabase.getAppDataBase(myContext)!!.countryDao()
        rvNewsArticle = view.findViewById(R.id.recyclerView)
        val manager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rvNewsArticle.layoutManager = manager
        rvNewsArticle.setLayoutManager(manager)
        rvNewsArticle.setItemAnimator(null);
        rvNewsArticle.addItemDecoration(GridItemDecoration(10, 2))
        productListModel.getProducts()


        productListModel.listOfProducts.observe(myContext, Observer(function = fun(productList: List<Countries>?) {
            productList?.let {
                var countryList =  ArrayList<Countries>();
                for(element in productList){
                   countryList.add(element)
                }
                countryDetail=countryList
                adapter = ProductListAdapter(countryList)
                rvNewsArticle.adapter = adapter


            }
        }))
        rvNewsArticle.addOnItemTouchListener(RecyclerItemClickListenr(myContext, rvNewsArticle, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                //do your work here..
                val newFragment = ProductDetailFragment.newInstance(countryDetail.get(position))
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.frag_container, newFragment)
                transaction.addToBackStack(null)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit()

            }
            override fun onItemLongClick(view: View?, position: Int) {
                System.out.println("item long click")
            }
        }))
        return view
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
                    .observe(ProductListFragment.myContext, object : Observer<List<Countries>> {
                        override fun onChanged(@Nullable deals: List<Countries>) {
                            if (deals.size == 0) {

                                adapter!!.setFilter(countryDetail);

                                //
                            } else {
                                var countryList =  ArrayList<Countries>();
                                for(element in deals){
                                    countryList.add(element)
                                }
                                adapter!!.setFilter(countryList);

                            }

                        }
                    })

            }
        }
    }
    override fun onAttach(activity: Activity) {
        ProductListFragment.myContext = activity as FragmentActivity
        super.onAttach(activity)
    }
}