package com.example.touristapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AlertDialogLayout
import androidx.appcompat.widget.AppCompatButton
//import androidx.fragment.app.FragmentResultListener
import com.example.touristapp.Model.Report
import com.example.touristapp.Model.Review
import com.example.touristapp.Model.TouristDatabase
import org.w3c.dom.Text


/**
 * The selected Review from the tourist_review_home is shown in this fragment.
 *
 */
class display_review : Fragment(R.layout.fragment_display_review) {
    // Options found in the dropdown menu when reporting a review
    lateinit var db: TouristDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { TouristDatabase(it) }!!
        /**
         * assigns variables to every element user interacts with
         */
        var item = Review("","","",0.0f,"", byteArrayOf(),0,0,)
        val imageReview = getView()?.findViewById<ImageView>(R.id.viewReview_Image)
        val reportBtn = getView()?.findViewById<AppCompatButton>(R.id.viewReview_reportBtn)
        val backBtn = getView()?.findViewById<AppCompatButton>(R.id.viewReview_backBtn)
        val title = getView()?.findViewById<TextView>(R.id.viewReview_titleTv)
        val description = getView()?.findViewById<TextView>(R.id.viewReview_description)
        val rating = getView()?.findViewById<RatingBar>(R.id.ratingBar)
        /**
         * ParentFragmentManager collects the data sent from the
         * tourist_display_review fragment
         */
//        parentFragmentManager.setFragmentResultListener("showRev",this, FragmentResultListener {
//                requestKey, result ->

            //val input = result.getSerializable("review") as Review
            val input = Review("","","",2.0f,"", byteArrayOf(),1,1)
            title?.text = input.title
            description?.text = input.description
            rating?.rating = input.rating
            item = input
            // On the occasion no image is found try catch will allow the app to continue
            try {
                val userImage: ByteArray = input.image
                val bmp: Bitmap = BitmapFactory.decodeByteArray(userImage, 0, userImage.size)
                imageReview?.setImageBitmap(bmp)
            }

            catch (e : NullPointerException) {
                Toast.makeText(activity,"No image",Toast.LENGTH_LONG).show()
            }
 //       })
        /**
         * returns the user to the previous fragment
         */

        backBtn?.setOnClickListener {
            var fragmentManager = requireActivity().supportFragmentManager
            var fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,tourist_review_home())
            fragmentTransaction.addToBackStack("display_review")
            fragmentTransaction.commit()
        }

        /**
         * Opens the an alert dialog where the user is able to submit a report.
         * The user input is sent to the report table
         */
        reportBtn?.setOnClickListener {
            val placeUserInfo = LayoutInflater.from(activity).inflate(R.layout.dialog_report_review,null)
            val dialog = android.app.AlertDialog.Builder(activity)
                .setTitle("Report Review")
                .setView(placeUserInfo)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", null).show()

            /**
             * sets the on click listener when the user is to press ok (the positive outcome)
             */
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val rgroup = view?.findViewById<RadioGroup>(R.id.report_optionGroup)

                val selectedId = rgroup.checkedRadioButtonId
                val choice: RadioButton = view?.findViewById(selectedId)
                val desc = view?.findViewById<EditText>(R.id.reportReview_description)
                //when ()
                val report = Report(item.username, desc.toString(),choice.toString(),1,item.userId)
                db.addReport(report)
                dialog.dismiss()
            }
        }
    }
}