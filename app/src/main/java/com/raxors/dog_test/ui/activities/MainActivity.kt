package com.raxors.dog_test.ui.activities

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.R


class MainActivity : AppCompatActivity() {

    var navView: BottomNavigationView? = null

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DogApplication.component.inject(this)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        progressBar = findViewById(R.id.progressBarMainActivity)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_list,
            R.id.navigation_favourites
        ))
        appBarConfiguration.fallbackOnNavigateUpListener
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}