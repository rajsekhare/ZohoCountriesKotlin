package com.example.zoho.viewmodel


import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zoho.MainApplication
import com.example.zoho.model.Countries
import com.example.zoho.repository.CountryDao
import com.example.zoho.repository.CountryRepo
import com.example.zoho.repository.DataRepository
import com.example.zoho.view.MainActivity
import com.example.zoho.view.ProductListFragment
import okhttp3.internal.Internal.instance
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class ProductViewModel(val dataRepository: DataRepository) : ViewModel(), KoinComponent {
    var countryAllList: LiveData<List<Countries>>? = null
    var filterTextAll = MutableLiveData<String>()
    var listOfProducts = MutableLiveData<List<Countries>>()
     lateinit var countryRepo: CountryRepo
    lateinit var countryDao: CountryDao
    private lateinit var myContext: Context
    lateinit var allCountries: LiveData<List<Countries>>


    init {
       System.out.println(ProductListFragment.myContext)
        countryRepo = CountryRepo(ProductListFragment.myContext)
        allCountries = countryRepo.getAllCountries()
        listOfProducts.value = listOf()

    }
    fun getProducts() {
        dataRepository.getProducts(object : DataRepository.OnProductData {
            override fun onSuccess(data: List<Countries>) {
                listOfProducts.value = data
                countryRepo.insert(data)

            }
//
            override fun onFailure() {
                //REQUEST FAILED
                System.out.println("failed");

            }
        })
    }


}