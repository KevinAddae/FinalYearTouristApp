package com.example.touristapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.touristapp.Model.Review


class GuestHome : Fragment(R.layout.fragment_guest_home) {
    lateinit var reviewList: List<Review>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reviewList = generateReview().toMutableList()
    }

    private fun generateReview():List<Review> {
        return listOf(
            Review("Bond","I Don't Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1),
            Review("Bond","I Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1)
        )
    }
}