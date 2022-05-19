package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.touristapp.Model.Password
import com.example.touristapp.Model.Tourist
import com.example.touristapp.Model.TouristDatabase

class Registration_part2 : AppCompatActivity() {
    var db: TouristDatabase = TouristDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_part2)
        //db = TouristDatabase(this)

        val etUsername = findViewById<EditText>(R.id.regi_Username)
        val etPassword = findViewById<EditText>(R.id.regi_password)
        val etVerify = findViewById<EditText>(R.id.regi_verifyPass)

        etUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0)
        etPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
        etVerify.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
    }

    fun registerNowBtnEvent (view: View) {
        var usern = findViewById<EditText>(R.id.regi_Username)
        var pass = findViewById<EditText>(R.id.regi_password)
        var vpass = findViewById<EditText>(R.id.regi_verifyPass)

        when{
            usern.text.toString().isEmpty() ||
                    pass.text.toString().isEmpty() ||
                    vpass.text.toString().isEmpty() -> Toast.makeText(getApplicationContext(),"Please make sure to fill in all fields",Toast.LENGTH_LONG).show()

            !pass.text.toString().equals(vpass.text.toString()) -> Toast.makeText(getApplicationContext(),"Ensure password and verify password are the same",Toast.LENGTH_LONG).show()

            else -> {
                var s: Tourist = db.getLastAddedTourist()
                s.username = usern.text.toString()
                db.updateTourist(s)
                db.addpassword(Password(0,pass.text.toString(),s.userID))
                var i = Intent(this,Tourist_MainMenu::class.java)
                startActivity(i)
            }

        }
    }
}