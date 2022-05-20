package com.example.touristapp

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Review
import com.example.touristapp.Model.ReviewAdapter
import com.example.touristapp.Model.TouristDatabase
import com.example.touristapp.Model.UserMap
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI

/**
 *  This serves as the home of the review, portion of the program
 *  This will branch of between two other fragments, which will create a review and show
 *  the selected review.
 */

class tourist_review_home : Fragment(R.layout.fragment_tourist_review_home) {
    var reviews = ArrayList<Review>()
    lateinit var reviewAdapter: ReviewAdapter

    /**
     * The things that will happen before the screen fully loads
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db = activity?.let { TouristDatabase(it) }

        //reviews = db!!.getTextReview()
        reviews = ArrayList(db!!.getTextReview())
        //reviews = ArrayList()
        //reviews.add(db!!.getReview(1))


        var reviewList = getView()?.findViewById<RecyclerView>(R.id.review_listOfReviews)

        reviewList?.layoutManager = LinearLayoutManager(activity)

        reviewAdapter = ReviewAdapter(requireActivity(), reviews, object: ReviewAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                /**
                 * bundle is used to allow for data to be communicated with other fragments
                 * in this particular example data is send to the display_review() fragment
                 */

                var bundle = Bundle()
                bundle.putSerializable("review",reviews[position])
                val fragment = display_review()
                fragment.arguments = bundle
                //parentFragmentManager.setFragmentResult("showRev",bundle)

                //This section will send the user to display_review() fragment
                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                // condition that checks the activity and uses the appropriate frame layout
                if (activity?.javaClass?.simpleName == "Guest_MainMenu") {
                    fragmentTransaction.replace(R.id.frameLayoutGuest,display_review())

                } else {
                    fragmentTransaction.replace(R.id.frameLayout, display_review())
                }
                fragmentTransaction.addToBackStack("tourist_review_home")
                fragmentTransaction.commit()
            }
        })
        reviewList?.adapter = reviewAdapter
        val confirmBtn = view?.findViewById<Button>(R.id.review_CreateReviewBtn)

        if (activity?.javaClass?.simpleName == "Guest_MainMenu"){
            confirmBtn.visibility = View.GONE
        }

        if (getCallerFragment().equals("Tourist_review_create")) {
            var bundle = this.arguments
            val input = bundle?.getSerializable("revItem") as Review

            reviews.add(input)
            reviewAdapter.notifyItemInserted(reviews.size - 1)


        }
        confirmBtn?.setOnClickListener {
            var fragmentManager = requireActivity().supportFragmentManager
            var fragmentTransaction = fragmentManager.beginTransaction()
            if (activity?.javaClass?.simpleName == "Guest_MainMenu") {
                fragmentTransaction.replace(R.id.frameLayoutGuest,Tourist_review_create())

            } else {
                fragmentTransaction.replace(R.id.frameLayout,Tourist_review_create())
            }
            fragmentTransaction.addToBackStack("Tourist_review_create")
            fragmentTransaction.commit()
        }
    }
    /**
     * gets the name of the last fragment
     */
    private fun getCallerFragment(): String? {
        val fm = fragmentManager
        val count = requireFragmentManager().backStackEntryCount
        return fm!!.getBackStackEntryAt(count - 1).name
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