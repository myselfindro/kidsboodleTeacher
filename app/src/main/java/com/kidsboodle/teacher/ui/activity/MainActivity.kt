package com.kidsboodle.teacher.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kidsboodle.teacher.R
import com.kidsboodle.teacher.databinding.ActivityMainBinding
import com.kidsboodle.teacher.ui.activity.login.LoginActivity
import com.kidsboodle.teacher.utility.goToActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private var mToolbarNavigationListenerIsRegistered: Boolean = false

    var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        setSupportActionBar(binding.layoutToolbar.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        changeBackButton()


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.dashboardTimetableFragment,
                R.id.studentsAttendanceFragment,
                R.id.navigationLiveClass,
                R.id.navigationAccount,
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(
            binding.layoutToolbar.toolbar,
            navController,
            appBarConfiguration
        )

        drawerLayout = binding.drawerLayout
        drawerLayout.setScrimColor(ContextCompat.getColor(applicationContext,R.color.nav_drawer_scrim_color))
        val navigationView = binding.drawerNavView
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.layoutToolbar.toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        navigationView.setupWithNavController(navController)
      //  setupDrawerContent(navigationView)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        displayHomeUpORHamburger(true)


    }

    fun displayHomeUpORHamburger(isHamburgerVisible: Boolean) {

        if (this::drawerLayout.isInitialized) {
            if (!isHamburgerVisible) {
                Log.d("locked", "LOCKED")
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

                //The order for this operation is disable first, then enable - VERY VERY IMPORTANT.

                toggle.isDrawerIndicatorEnabled = false

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow)


                if (!mToolbarNavigationListenerIsRegistered) {
                    toggle.setToolbarNavigationClickListener {
                        onBackPressed()
                    }
                    mToolbarNavigationListenerIsRegistered = true
                }
            } else {
                Log.d("locked", "UNLOCKED")
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                //The order for this operation is disable first, then enable - VERY VERY IMPORTANT.

                supportActionBar?.setDisplayHomeAsUpEnabled(true)


                toggle.isDrawerIndicatorEnabled = true

                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamberger)
                toggle.toolbarNavigationClickListener = null
                mToolbarNavigationListenerIsRegistered = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        mMenu = menu
        return true
    }

    fun changeBackButton() {
        val toolbar: MaterialToolbar = binding.layoutToolbar.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_left_arrow)
        setSupportActionBar(toolbar)
    }

    fun changesBackButton() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamberger)
    }

    fun changeToBackIcon() {
        if (this::binding.isInitialized) {
            val toolbar: MaterialToolbar = binding.layoutToolbar.toolbar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_left_arrow)
            setSupportActionBar(toolbar)
        }
    }

    fun setToolbarLeftIcon(icon: Int?) {
        if (this::binding.isInitialized) {

            val toolbar: MaterialToolbar = binding.layoutToolbar.toolbar
            if (icon != null) {
                toolbar.logo = ContextCompat.getDrawable(this, icon)
            } else {
                toolbar.logo = null
            }

            setSupportActionBar(toolbar)
        }
    }


    fun changeToolbarTitle(Title: String) {

        if (this::binding.isInitialized) {
//            val toolbar: MaterialToolbar = binding.layoutToolbar.toolbar
//            toolbar.title =Title
//            setSupportActionBar(toolbar)
            supportActionBar?.title = Title
        }

    }
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(this)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            R.id.LogOut -> {
                print("logout=============")
                Toast.makeText(this,"Logged out",Toast.LENGTH_SHORT).show()
                this.goToActivity(LoginActivity::class.java, true)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateLogout() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return true
    }

    fun hideDrawerNavigation() { //Hide drawer navigation bar
        binding.drawerNavView.visibility = View.GONE
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun showDrawerNavigation() { //Show drawer navigation bar
        if (this::binding.isInitialized) {
            binding.drawerNavView.visibility = View.VISIBLE
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}