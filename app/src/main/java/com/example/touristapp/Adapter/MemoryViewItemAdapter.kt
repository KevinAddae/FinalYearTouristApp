package com.example.touristapp.Model


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R
import java.io.IOException


private const val TAG = "MemoryAdapter"
class MemoryViewItemAdapter(val context: Context, val memory: List<Memories>,
                            val onClickListener: OnClickListener) : RecyclerView.Adapter<MemoryViewItemAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.memories_view_singleitem, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain postions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }

        val location = holder.itemView.findViewById<TextView>(R.id.memories_view_single_item_title)
        val image = holder.itemView.findViewById<ImageView>(R.id.memories_view_single_item_img)


        location.text = memory[position].location
        try {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(
                memory[position].image,
                0,
                memory[position].image.size
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
    override fun getItemCount() = memory.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}