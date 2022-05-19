package com.example.touristapp.Model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R

private const val TAG = "MapsAdapter"
class MapsAdapter(val context: Context, val futurelocal: List<UserMap>, val onClickListener: OnClickListener,
                  val onItemLongClickListener: OnItemLongClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(position: Int, popm: PopupMenu)
    }

    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_view_interaction_animation, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain postions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val local = futurelocal[position]
        //sets up the on click listener to take position of recycler view
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            onClickListener.onItemClick(position)
        }
        //sets up the on long click listener to take position of recycler view
        holder.itemView.setOnLongClickListener {
            // inflates the popup menu
            var popm = PopupMenu(context!!.applicationContext,holder.itemView)
            popm.inflate(R.menu.recycler_options)
            popm.show()
            onItemLongClickListener.onItemLongClicked(position, popm)
            return@setOnLongClickListener true
        }

        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.rv_listTitle)
        textViewTitle.text = local.title
    }

    /**
     * get size of size of the list
     */
    override fun getItemCount() = futurelocal.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}