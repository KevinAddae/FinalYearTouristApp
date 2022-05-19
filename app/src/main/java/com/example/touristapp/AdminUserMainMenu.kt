package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Tourist
import com.example.touristapp.Model.TouristDatabase
import com.example.touristapp.Model.UserAdapter

class AdminUserMainMenu : AppCompatActivity() {
    lateinit var touristAdapter: UserAdapter
    var touristInfo =  ArrayList<Tourist>()
    var db = TouristDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_main_menu)
        // gets all the tourist in the tourist table
        touristInfo.addAll(db.getAllTourist())


        var recycler = findViewById<RecyclerView>(R.id.admin_user_recycler)

        recycler.layoutManager = LinearLayoutManager(this)

        touristAdapter = UserAdapter(this, touristInfo, object : UserAdapter.OnClickListener {
            override fun onItemClick(position: Int, popMenu: PopupMenu) {
                popMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        // adds a listener to the delete option in the pop up
                        R.id.popup_admin_Delete -> {
                            //removes tourist info in the arraylist then updates adapter
                            touristInfo.removeAt(position)
                            touristAdapter.notifyItemRemoved(touristInfo.size - 1)
                            // removes tourist in the database
                            db.deleteTourist(touristInfo[position])
                            return@setOnMenuItemClickListener true
                        }
                        // adds a listener to the notification option in the pop up
                        R.id.popup_admin_Notification -> {
                            //changes the activity
                            val i = Intent(this@AdminUserMainMenu, AdminNotification::class.java)
                            i.putExtra("activity", "adminUserMainMenu")
                            //sends the item selected
                            i.putExtra("notification",touristInfo[position])
                            startActivity(i)
                            return@setOnMenuItemClickListener true
                        }
                        // adds a listener to the report option in the pop up
                        R.id.popup_admin_Report -> {
                            val i = Intent(this@AdminUserMainMenu, AdminReportMenu::class.java)
                            i.putExtra("report",touristInfo[position])
                            startActivity(i)
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            }

        })
        recycler.adapter = touristAdapter
    }

    private fun generateTourist(): ArrayList<Tourist> {
        return arrayListOf(Tourist(1,"franceLover","Cherry","Glober","cherryglober@gmail.com"),
            Tourist(2,"starGazer","Tom","Rapmid","Rapmeind@wadio")
        )
    }
}