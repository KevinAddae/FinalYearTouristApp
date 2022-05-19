package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Report
import com.example.touristapp.Model.ReportAdapter
import com.example.touristapp.Model.Tourist

class AdminReportMenu : AppCompatActivity() {
    lateinit var reports: ArrayList<Report>
    lateinit var reportAdapter: ReportAdapter
    lateinit var temp: ArrayList<Report>
    lateinit var input: Report

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_report_menu)
        var info = intent.extras;
        if (info != null) {
            input = (info.getSerializable("report") as Report?)!!
        }
        var recycler = findViewById<RecyclerView>(R.id.admin_report_recycler)
        reports = generateReport()
        temp = generateReport()
        recycler.layoutManager = LinearLayoutManager(this)

        reportAdapter = ReportAdapter(this,reports,temp,object : ReportAdapter.OnClickListener {
            override fun onItemClick(position: Int, popm: PopupMenu) {
                popm.setOnMenuItemClickListener{
                    when(it.itemId) {
                        R.id.popup_admin_userinfo -> {
                            var i = Intent(this@AdminReportMenu,AdminUserMainMenu::class.java)
                            startActivity(i)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.popup_admin_removeReport -> {
                            reports.removeAt(position)
                            temp.removeAt(position)
                            reportAdapter.notifyItemRemoved(position)
                            return@setOnMenuItemClickListener true
                        }
                        else -> {return@setOnMenuItemClickListener false}
                    }
                }
            }

        })
        recycler.adapter = reportAdapter

    }
    //Sets up the search function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //adds the search button to the top of the action bar
        menuInflater.inflate(R.menu.admin_search,menu)
        var mitem = menu?.findItem(R.id.admin_menu_search)
        var searchV = mitem?.actionView as SearchView
        searchV.imeOptions = EditorInfo.IME_ACTION_DONE
        //adds a listener to the search
        searchV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // compulsory method, does not do anything
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // Method is called every time letter is added or removed in search query
            override fun onQueryTextChange(newText: String?): Boolean {
                //filters items that do not match the newText (User Input)
                var act = intent.extras
                var activeName = act?.getString("activity")

                reportAdapter!!.filter.filter(newText)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
    private fun generateReport(): ArrayList<Report> {
        return arrayListOf(Report("Frensoir","He has been very disrespectful about" +
                "the location they are talking about","Abuse",1,2),
            Report("Rob","He has been very disrespectful about" +
                    "the location they are talking about","Abuse",1,2),
            Report("Caty","He has been very disrespectful about" +
                    "the location they are talking about","Abuse",1,2))
    }
}