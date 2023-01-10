package com.learning.androidroadmap.navcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.androidroadmap.R

class ActivityNavigationComponent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation_component)

        supportActionBar?.hide()
    }
}