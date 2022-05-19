package com.example.touristapp.Model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R

private const val TAG = "MapsAdapter"
class SingleMarkerAdapter(val context: Context, val user: List<Future_location>, val onClickListener: OnClickListener) : RecyclerView.Adapter<SingleMarkerAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item_markers, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain positions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val local = user[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        val name = holder.itemView.findViewById<TextView>(R.id.maps_singleItem_name)
        val desc = holder.itemView.findViewById<TextView>(R.id.maps_singleItem_desc)
        val location = holder.itemView.findViewById<TextView>(R.id.maps_singleItem_local)
        val country = holder.itemView.findViewById<TextView>(R.id.maps_singleItem_country)
        val booked = holder.itemView.findViewById<RadioButton>(R.id.maps_singleItem_booked)

        name.text = local.name
        desc.text = local.description
        location.text = local.city_town
        country.text = local.country
        if (local.booked.toBoolean() == true)
            booked.performClick()

    }

    /**
     * get size of size of the list
     */
    override fun getItemCount() = user.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}