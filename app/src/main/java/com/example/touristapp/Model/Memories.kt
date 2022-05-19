package com.example.touristapp.Model

import java.io.Serializable

data class Memories(var location: String, var image: ByteArray, var memoryId: Int, var userId: Int) : Serializable