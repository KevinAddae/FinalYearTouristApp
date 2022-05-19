package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.touristapp.Model.Tourist
import com.google.android.material.navigation.NavigationView



class Tourist_MainMenu : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_main_menu)

        drawerLayout = findViewById(R.id.drawerLayoutMainMenu)
        val navView: NavigationView = findViewById(R.id.nav_viewMainMenu)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,Tourist_Home())
            commit()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {


            it.isChecked = true

            when (it.itemId) {

                R.id.nav_home -> replaceFragement(Tourist_Home(),it.title.toString())

                R.id.nav_future_location -> replaceFragement(Tourist_Future_Interest(),it.title.toString())

                R.id.nav_map -> replaceFragement(DisplayMap(), it.title.toString())

                R.id.nav_review -> replaceFragement(tourist_review_home(),it.title.toString())

                R.id.nav_memories -> replaceFragement(Tourist_Memories(), it.title.toString())
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
        fragmentTransaction.replace(R.id.frameLayout,fragment)
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

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}