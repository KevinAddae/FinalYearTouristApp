package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.touristapp.Model.Password
import com.example.touristapp.Model.Tourist
import com.example.touristapp.Model.TouristDatabase

/**
 * This class serves as the connector for the login screen. It will send the user to several different
 * activities such as register, logged in tourist and guest activites.
 */
var example = Tourist(1,"k","Kevin","Addae","awdi@myn")
var pass: Password = Password(1,"rain",1)

lateinit var db: TouristDatabase
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         db = TouristDatabase(this)
        var p: Tourist = db.getTourist(1)

        //AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        /**
         * Adds images next to the corresponding edit views
         */
        val etUsername = findViewById<EditText>(R.id.edit_username)
        val etPassword = findViewById<EditText>(R.id.edit_password)
        etUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0)
        etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)

    }

    /**
     * When the login button is pressed the user is inputs are checked, this also includes error handling
     * and send the user to the tourist main menu activity.
     */
    fun loginBtnEvent(view: View) {

        //val alertDialog: AlertDialog
        //alertDialog.setTitle("a")
        //Tourist_MainMenu
        var username = findViewById<EditText>(R.id.edit_username)
        var password = findViewById<EditText>(R.id.edit_password)
        var userN = Tourist(0,"invalid","invalid","invalid","invalid")
        var pas = Password(0,"invalid",0)
        if (username.text.isNotEmpty() || password.text.isNotEmpty()) {
             userN = db.getUsername(username.toString())
             pas = db.getPasswordUser(userN.userID)
        }
        when{
            username.text.isEmpty() || password.text.isEmpty()-> Toast.makeText(getApplicationContext(),"Please fill in both username and password", Toast.LENGTH_LONG).show()

            username.text.toString() == example.username && password.text.toString() == pass.password -> {
                val i = Intent(this, Tourist_MainMenu::class.java)
                startActivity(i)
            }

            username.text.toString() == "admin" && password.text.toString() == "admin123" -> {
                val i = Intent(this, AdminMainMenu::class.java)
                startActivity(i)
            }
            username.text.toString() == userN.username && password.text.toString() == pas.password -> {
                val i = Intent(this, Tourist_MainMenu::class.java)
                startActivity(i)
            }
            else-> Toast.makeText(getApplicationContext(),"User details are incorrect",Toast.LENGTH_LONG).show()
        }
    }

    /**
     * When the register button is pressed the user is sent to the register activity
     */
    fun registerBtnEvent(view: View) {
        var i = Intent(this,Registration::class.java)
        startActivity(i)
    }

    fun skipBtnEvent(view: View) {
        var i = Intent(this,Guest_MainMenu::class.java)
        startActivity(i)
    }
}