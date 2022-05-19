package com.example.touristapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class Guest_MainMenu : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main_menu)

        drawerLayout = findViewById(R.id.drawerLayoutGuestMainMenu)
        val navView: NavigationView = findViewById(R.id.nav_viewGuestMainMenu)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutGuest,GuestHome())
            commit()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {


            it.isChecked = true

            when (it.itemId) {

                R.id.guestNav_home -> replaceFragement(GuestHome(),it.title.toString())

                R.id.guestNav_map -> replaceFragement(DisplayMap(), it.title.toString())

                R.id.guestNav_review -> replaceFragement(tourist_review_home(),it.title.toString())
            }
            true
        }

        //var check: Tourist = db.getLastAddedTourist()

        //Toast.makeText(applicationContext,"Welcome ${check.firstname} ${check.lastname}",Toast.LENGTH_LONG).show()


    }

    /**
     * This is called to change the current fragment.
     * Fragment is the fragment that is going to be shown, and title
     * is what the fragment will be known as in the back stack
     *
     */
    private fun replaceFragement(fragment: Fragment, title: String){

        val fragmentManager =  supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutGuest,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
        fragmentTransaction.addToBackStack(title)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}