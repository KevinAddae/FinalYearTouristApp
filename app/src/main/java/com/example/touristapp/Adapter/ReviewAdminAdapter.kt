package com.example.touristapp.Model


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R
import java.io.IOException

private const val TAG = "ReviewAdapter"
class ReviewAdminAdapter(val context: Context, var rev: MutableList<Review>, var temp: List<Review>,
                         val onClickListener: OnClickListener) : RecyclerView.Adapter<ReviewAdminAdapter.ViewHolder>(), Filterable {

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
        val local = rev[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        holder.itemView.findViewById<TextView>(R.id.review_single_title).text = local.title
        holder.itemView.findViewById<TextView>(R.id.review_single_Username).text = local.username

        val image = holder.itemView.findViewById<ImageView>(R.id.review_single_img)
        try {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(
                rev[position].image,
                0,
                rev[position].image.size
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

    /**
     * getFilter is part of the filterable interface
     * Its aim is to filter the items of an recycler view
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            // this method performs the filtering
            override fun performFiltering(charsequence: CharSequence?): FilterResults {
                // A list of filtered items
                var filterList = ArrayList<Review>()
                // checks if the user input
                if (charsequence == null || charsequence.isEmpty()) {
                    filterList.addAll(temp)

                } else {

                    var searchChr: String = charsequence.toString().lowercase().trim()
                    for (exampleRev in temp) {
                        if (exampleRev.title.lowercase().contains(searchChr))
                            filterList.add(exampleRev)
                    }
                }
                var results = FilterResults()
                results.values = filterList
                return results
            }
            // publishes the results of the filtering
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                rev.clear()
                rev.addAll(results?.values as List<Review>)
                notifyDataSetChanged()
            }
        }
    }


}