package com.example.touristapp.Model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R
import org.w3c.dom.Text
import java.io.IOException

private const val TAG = "ReviewAdapter"
class ReviewAdapter(val context: Context, val rev: List<Review>,
                    val onClickListener: OnClickListener) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_single_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain postions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = rev[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        val title = holder.itemView.findViewById<TextView>(R.id.review_single_title)
        val username = holder.itemView.findViewById<TextView>(R.id.review_single_Username)
        val image = holder.itemView.findViewById<ImageView>(R.id.review_single_img)
        title.text = info.title
        username.text = info.username

        try {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(
                info.image,
                0,
                info.image.size
            )
            image.setImageBitmap(bmp)
        } catch (e: IOException) {
            Toast.makeText(context,"Image cannot be found", Toast.LENGTH_LONG).show()
        } catch (e: NullPointerException) {
            Toast.makeText(context,"Image cannot be found", Toast.LENGTH_LONG).show()
        }


    }

    /**
     * get size of size of the list
     */
    override fun getItemCount() = rev.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}