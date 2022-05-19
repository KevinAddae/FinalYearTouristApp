package com.example.touristapp.Model

import java.io.Serializable

data class Review(
    var username:String, var title: String, var description: String, var rating: Float, var countryReview: String, var image: ByteArray,
    var reviewId:Int, var userId: Int) : Serializable