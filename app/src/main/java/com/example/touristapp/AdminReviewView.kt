package com.example.touristapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.touristapp.Model.Review
import java.io.IOException

class AdminReviewView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_review_view)
        // collects the data sent from the review main menu fragment
        val rev = intent.getSerializableExtra("display") as Review

        val title = findViewById<TextView>(R.id.admin_review_title)
        val descr = findViewById<TextView>(R.id.admin_review_descr)
        val rating = findViewById<RatingBar>(R.id.admin_review_ratingbar)
        val img = findViewById<ImageView>(R.id.admin_review_img)

        title.text = rev.title
        descr.text = rev.description
        rating.numStars = rev.rating.toInt()

        try {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(
                rev.image,
                0,
                rev.image.size
            )
            img.setImageBitmap(bmp)
        } catch (e: IOException) {
            Toast.makeText(this,"Image cannot be found", Toast.LENGTH_LONG).show()
        } catch (e: NullPointerException) {
            Toast.makeText(this,"Image cannot be found", Toast.LENGTH_LONG).show()
        }

    }

    fun deleteReviewEvent (view: View) {
        //calls the delete from the database handler

        //returns back to previous activity
        val i = Intent(this,AdminReview::class.java)
        startActivity(i)
    }
}