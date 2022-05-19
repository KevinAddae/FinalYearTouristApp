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

private const val TAG = "ReviewAdapter"
class UserAdapter(val context: Context, val user: List<Tourist>,
                  val onClickListener: OnClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int, popMenu: PopupMenu)
    }

    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tourist_single_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain postions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = user[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            var popm = PopupMenu(context!!.applicationContext,holder.itemView)
            popm.inflate(R.menu.admin_user_recycler_options)
            popm.show()
            onClickListener.onItemClick(position,popm)
        }

        val firstname = holder.itemView.findViewById<TextView>(R.id.admin_user_singleitem_firstname)
        val username = holder.itemView.findViewById<TextView>(R.id.admin_user_singleitem_username)
        val email = holder.itemView.findViewById<TextView>(R.id.admin_user_singleitem_email)

        firstname.text = info.firstname
        username.text = info.username
        email.text = info.email


    }

    /**
     * get size of size of the list
     */
    override fun getItemCount() = user.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}