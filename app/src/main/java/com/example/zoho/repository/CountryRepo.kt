package com.example.zoho.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.zoho.database.AppDatabase
import com.example.zoho.model.Countries
import com.example.zoho.view.ProductListFragment
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CountryRepo(application: Context) {

    private var countryDaos: CountryDao? = null
    private var listLiveData: LiveData<List<Countries>>

    init {
        Executors.newSingleThreadExecutor()
        Executors.newFixedThreadPool(3)
        val appDatabase = AppDatabase.getAppDataBase(application)
        countryDaos = appDatabase?.countryDao()!!
        listLiveData = countryDaos?.getCountries()!!
    }

    fun getAllCountries(): LiveData<List<Countries>> {
        return listLiveData
    }

    fun insert(word: List<Countries>) {

        countryDaos?.let { insertAsyncTask(it).execute(word) }
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: CountryDao) : AsyncTask<List<Countries>, Void, Void>() {

        override fun doInBackground(vararg params: List<Countries>): Void? {

            mAsyncTaskDao.insertAll(params[0])

            return null
        }
    }

}