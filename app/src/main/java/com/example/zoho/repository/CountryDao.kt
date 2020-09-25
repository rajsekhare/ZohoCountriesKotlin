package com.example.zoho.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zoho.model.Countries
import javax.sql.DataSource


@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: List<Countries>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(country: List<Countries>)

    @Query("SELECT * FROM countries where name collate SQL_Latin1_General_CP1_CI_AS like :name")
    fun getCountryByName(name: String): LiveData<List<Countries>>

    @Query("SELECT * FROM countries")
    fun getCountries(): LiveData<List<Countries>>
}