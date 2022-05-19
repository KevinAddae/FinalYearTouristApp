package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/**
 * This serves as the main menu for the admins login.
 * In the Activity_admin_main_menu several different elements for each element calls
 * the following methods.
 */
class AdminMainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main_menu)
    }

    fun reviewBtnEvent (view: View) {
        val i = Intent(this, AdminReview::class.java)
        startActivity(i)
    }

    fun reportBtnEvent (view: View) {
        val i = Intent(this, AdminReportMenu::class.java)
        startActivity(i)
    }

    fun userBtnEvent (view: View) {
        val i = Intent(this, AdminUserMainMenu::class.java)
        startActivity(i)
    }

    fun SignOutBtn (view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}