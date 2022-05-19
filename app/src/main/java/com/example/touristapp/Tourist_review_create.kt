package com.example.touristapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.media.MediaBrowserServiceCompat.RESULT_OK
import com.example.touristapp.Model.Review
import com.example.touristapp.Model.TouristDatabase
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException

/**
 * This fragment will allow for a review to be created and stored to a database.
 * Once the review has been added the view will change back to review_display fragment.
 */
private const val TAG = "tourist_review_create"

class Tourist_review_create : Fragment(R.layout.fragment_tourist_review_create) {

    private var db = TouristDatabase(requireActivity())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view?.findViewById<ImageView>(R.id.createReview_uploadedPicture)

        /**
         *  returns data from the gallery intent and uses the data
         */
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImage = data?.data
                // converts the image type from Uri to bitmap
                val bitmapImage = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                // sets the image to the image view
                imageView?.setImageBitmap(bitmapImage)
            }
        }

        val uploadBtn = view?.findViewById<Button>(R.id.createReview_UploadPictureBtn)

        /**
         * When the Upload button is pressed the user is sent to the gallery
         */
        uploadBtn?.setOnClickListener {
            Log.i(TAG, "btn press")
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            //returns the data selected
            resultLauncher.launch(galleryIntent)
            Log.i(TAG, "Go to Gallery")
        }

        val confirmBtn = view?.findViewById<Button>(R.id.createReview_confirmBtn)
        val title = view?.findViewById<EditText>(R.id.createReview_Title)?.text.toString()
        val descr = view?.findViewById<EditText>(R.id.createReview_Description)?.text.toString()
        val rating = view?.findViewById<RatingBar>(R.id.createReview_Ratingbar)?.rating?.toFloat()

        /**
         * Sets the on click listeners for both of the buttons.
         */
        confirmBtn?.setOnClickListener {
            if (title.isEmpty() || descr.isEmpty() || rating!!.equals(null)) {
                Toast.makeText(
                    requireActivity(),
                    "Please Make sure all inputs have been added",
                    Toast.LENGTH_LONG
                )

            } else {
                // converts the image from bitmap to byteArray
                val bitmap = imageView.drawable.toBitmap()
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG,90,stream)
                val image = stream.toByteArray()

                //creates object review to store into a database
                val rev: Review = rating?.let { it1 ->
                    Review(
                        "Hi", title, descr,
                        it1, "P", image, 1, 1
                    )
                }!!
                db.addReview(rev)


                var fragmentManager = requireActivity().supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, tourist_review_home())
                fragmentTransaction.addToBackStack("Tourist_review_create")
                fragmentTransaction.commit()
            }
        }

        /**
         * This is part of the listener event. Set the type of intent aimed.
         * Sends the view to there gallery app on the mobile.
         */


//        @Override
//        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            if (resultCode == Activity.RESULT_OK && data != null) {
//                val selectedImage = data.data
//                val bitmapImage = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
        //               imageView?.setImageBitmap(bitmapImage)
//            }
//        }
    }

}