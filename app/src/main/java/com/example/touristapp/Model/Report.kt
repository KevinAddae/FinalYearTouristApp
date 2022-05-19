package com.example.touristapp.Model

import java.io.Serializable

data class Report(var username: String, var description:String?, var type:String?,
                  var reportId: Int, var userId: Int): Serializable