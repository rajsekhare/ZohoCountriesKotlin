package com.example.zoho.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "countries", indices = [Index(value = ["name"], unique = true)])
data class Countries (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val alpha3Code:String,
    val name : String,
    val capital : String,
    val region : String,
    val subregion : String,
    val population : Int,
    val area : Double,
//  val currencies : List<Currencies>,
//  val languages : List<Languages>,
    val flag : String

): Serializable


