package com.pramodk.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.pramodk.navigationdrawer.fragment.HomeFragment
import com.pramodk.navigationdrawer.fragment.PhotosFragment
import com.pramodk.navigationdrawer.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    var navigationPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.DrawerLayout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)

        initNavigationDrawer()
        displayScreen(-1)
    }
    private fun initNavigationDrawer() {

        // Pass the ActionBarToggle action into the drawerListener
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        drawerToggle.syncState()

        //toolbar
        setSupportActionBar(toolbar)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //setup for navigation
        navigationView.setNavigationItemSelectedListener { menuItem ->
            displayScreen(menuItem.itemId)
            drawerLayout.closeDrawers()
            true
        }

    }

    private fun displayScreen(id: Int){

        //Load Home fragment first
        navigationPosition = R.id.home
        navigateToFragment(HomeFragment.newInstance())
        navigationView.setCheckedItem(navigationPosition)
        toolbar.title = "home"

        when (id){
            R.id.home -> {
                toolbar.title = "home"
                navigationPosition = R.id.home
                navigateToFragment(HomeFragment.newInstance())
            }

            R.id.photos-> {
                toolbar.title = "photos"
                navigationPosition = R.id.photos
                navigateToFragment(PhotosFragment.newInstance())
            }

            R.id.profile -> {
                toolbar.title = "profile"
                navigationPosition = R.id.profile
                navigateToFragment(ProfileFragment.newInstance())
            }

        }
    }
    private fun navigateToFragment(fragmentToNavigate: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragmentToNavigate)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}