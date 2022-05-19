package com.example.touristapp.Model

import java.io.Serializable

data class Tourist(var userID:Int, var username: String, var firstname: String, var lastname:String,  var email:String):
    Serializable