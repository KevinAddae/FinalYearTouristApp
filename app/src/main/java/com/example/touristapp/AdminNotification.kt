package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.touristapp.Model.Tourist

class AdminNotification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_notification)
        var input: Tourist?
        var info = intent.extras;
        if (info != null) {
            input = info.getSerializable("notification") as Tourist?
        }

        var sendbtn = findViewById<Button>(R.id.admin_Notification_sendBtn)
        var desc = findViewById<EditText>(R.id.admin_Notification_desc)

        var i = Intent(this,AdminUserMainMenu::class.java)
        startActivity(i)

        sendbtn.setOnClickListener {
            if (desc.text.isEmpty())
                Toast.makeText(this,"Make sure there is a description",Toast.LENGTH_LONG).show()
            else {
                var i = Intent(this,AdminUserMainMenu::class.java)
                startActivity(i)
            }
        }

    }
}