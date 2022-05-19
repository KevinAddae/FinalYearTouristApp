package com.example.touristapp.Model


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.R
import org.w3c.dom.Text

private const val TAG = "AdminReview"
class ReportAdapter(val context: Context, var report: MutableList<Report>, var temp: List<Report>,
                    val onClickListener: OnClickListener) : RecyclerView.Adapter<ReportAdapter.ViewHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: Int, popm: PopupMenu)
    }

    /**
     * inflate the view and wraps that around the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_single_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * gets the data from a certain postions and bind it to the view, also the text that is shown in
     * the recycle view.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rep = report[position]
        holder.itemView.setOnClickListener {
            Log.i(TAG,"Tapped on position $position")
            var popm = PopupMenu(context!!.applicationContext,holder.itemView)
            popm.inflate(R.menu.admin_report_option)
            popm.show()
            onClickListener.onItemClick(position,popm)
        }
        val username = holder.itemView.findViewById<TextView>(R.id.admin_report_username)
        val type = holder.itemView.findViewById<TextView>(R.id.admin_report_type)
        val desc = holder.itemView.findViewById<TextView>(R.id.admin_report_desc)

        username.text = rep.username
        type.text = rep.type
        desc.text = rep.description

    }

    /**
     * get size of size of the list
     */
    override fun getItemCount() = report.size

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
                var filterList = ArrayList<Report>()
                // checks if the user input
                if (charsequence == null || charsequence.isEmpty()) {
                    filterList.addAll(temp)

                } else {

                    var searchChr: String = charsequence.toString().lowercase().trim()
                    for (exampleRev in temp) {
                        if (exampleRev.username.lowercase().contains(searchChr))
                            filterList.add(exampleRev)
                    }
                }
                var results = FilterResults()
                results.values = filterList
                return results
            }
            // publishes the results of the filtering
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                report.clear()
                report.addAll(results?.values as List<Report>)
                notifyDataSetChanged()
            }
        }
    }


}