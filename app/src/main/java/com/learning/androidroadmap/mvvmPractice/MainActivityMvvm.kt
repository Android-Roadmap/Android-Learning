package com.learning.androidroadmap.mvvmPractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.learning.androidroadmap.R
import com.learning.androidroadmap.databinding.ActivityMainMvvmBinding
import com.learning.androidroadmap.mvvmPractice.viewModels.MainActivityViewModel

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
        navController.addOnDestinationChangedListener{ _, _, _ ->
           val currentFrag = navController.currentDestination
            supportActionBar?.title = currentFrag?.label
            when(currentFrag?.id) {
                R.id.fragment_content ->{
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
        val viewModel = ViewModelProvider(this,getViewModelFactory { MainActivityViewModel()
                                })[MainActivityViewModel::class.java]
        //Login state control
        viewModel.checkLoginState()
        viewModel.loginState.observe(this){ hasLoginState ->
            val graph = navController.navInflater.inflate(R.navigation.navigation_component_mvvm)
            if (hasLoginState){
                graph.setStartDestination(R.id.fragment_content)
            }else{
                graph.setStartDestination(R.id.loginFragmentNew)
            }
            navController.graph = graph
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}