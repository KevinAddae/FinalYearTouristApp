package com.example.touristapp.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Future_location(
    var name: String?, var country:String, var city_town:String,
    var description: String, var booked: String, val futureLocationID: Int, val userId:Int,
    val latitude: Double, val longitude: Double): Serializable {

}