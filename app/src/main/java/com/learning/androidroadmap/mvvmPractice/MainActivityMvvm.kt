package com.learning.androidroadmap.mvvmPractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.learning.androidroadmap.databinding.ActivityMainMvvmBinding

class MainActivityMvvm : AppCompatActivity() {
    lateinit var binding: ActivityMainMvvmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMvvmBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}