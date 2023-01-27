package com.learning.androidroadmap.mvvmPractice


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.ActivityMainMvvmBinding

class MainActivityMvvm : AppCompatActivity() {
    private lateinit var binding: ActivityMainMvvmBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMvvmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.fragment_nav_host)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.title = navController.currentDestination?.label
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}