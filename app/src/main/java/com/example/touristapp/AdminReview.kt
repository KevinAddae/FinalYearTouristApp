package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Review
import com.example.touristapp.Model.ReviewAdminAdapter
import java.util.*
import kotlin.collections.ArrayList

class AdminReview : AppCompatActivity() {
    lateinit var reviews: MutableList<Review>
    lateinit var tempReviews: MutableList<Review>

    lateinit var reviewAdapter: ReviewAdminAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_review)

        reviews = generateReview()
        tempReviews = generateReview().toMutableList()
        val reviewRecycler = findViewById<RecyclerView>(R.id.admin_reviews_recyclerView)
        reviewRecycler.layoutManager = LinearLayoutManager(this)
        reviewAdapter = ReviewAdminAdapter(this, reviews,tempReviews, object: ReviewAdminAdapter.OnClickListener {
            /**
             * on the clicked position the relevant data is collected and sent to
             * the display intent
             */
            override fun onItemClick(position: Int) {
                val i = Intent(applicationContext, AdminReviewView::class.java)
                i.putExtra("display", reviews[position])
                startActivity(i)
            }
        })
        reviewRecycler?.adapter = reviewAdapter


    }

    private fun generateReview():MutableList<Review> {
        return mutableListOf(
            Review("Bond","I Don't Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1),
            Review("Bond","I Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1),
            Review("Bond","The Blues","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1)
        )
    }
    //Sets up the search function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //adds the search button to the top of the action bar
        menuInflater.inflate(R.menu.admin_search,menu)
        var mitem = menu?.findItem(R.id.admin_menu_search)
        var searchV = mitem?.actionView as SearchView
        searchV.imeOptions = EditorInfo.IME_ACTION_DONE
        //adds a listener to the search
        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // compulsory method, does not do anything
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // Method is called every time letter is added or removed in search query
            override fun onQueryTextChange(newText: String?): Boolean {
                //filters items that do not match the newText (User Input)
                reviewAdapter!!.filter.filter(newText)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}