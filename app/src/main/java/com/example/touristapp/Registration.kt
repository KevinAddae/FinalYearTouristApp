package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.touristapp.Model.Tourist
import com.example.touristapp.Model.TouristDatabase

/**
 * serves as the first registration screen
 */
class Registration : AppCompatActivity() {
    var db: TouristDatabase = TouristDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val etEmail = findViewById<EditText>(R.id.regi_Email)
        val etFirstname = findViewById<EditText>(R.id.edit_firstname)
        val etLastname = findViewById<EditText>(R.id.regi_Lastname)

        etEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_email_24, 0, 0, 0)
        etFirstname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0)
        etLastname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0)

    }

    fun nextBtnEventHandler(view: View) {
        val first = findViewById<EditText>(R.id.edit_firstname)
        val last = findViewById<EditText>(R.id.regi_Lastname)
        val em = findViewById<EditText>(R.id.regi_Email)

        when{
            first.text.isEmpty() || last.text.isEmpty() || em.text.isEmpty() ->
                Toast.makeText(getApplicationContext(),"Please fill in all the fields",Toast.LENGTH_SHORT).show()

            !em.text.contains("@") -> Toast.makeText(getApplicationContext(),"Ensure the email is correct",Toast.LENGTH_LONG).show()

            check_emails() == true -> {
                Toast.makeText(getApplicationContext(),"The email is already in use",Toast.LENGTH_SHORT).show()

            }
            else -> {
                db.addTourist(Tourist(0,"", first.toString(),last.toString(),em.toString()))

                val i = Intent(this, Registration_part2::class.java)
                startActivity(i)
            }
        }

    }



    fun check_emails(): Boolean{
        val emails: ArrayList<String> = db.getEmail()
        var value = false
        emails.forEach{ e ->
            if (findViewById<EditText>(R.id.regi_Email).equals(e))
                value = true
        }
        return value
    }

}