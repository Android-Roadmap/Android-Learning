package com.learning.androidroadmap.mvvmPractice.applicationclass

import com.learning.androidroadmap.mvvmPractice.Calculator
import com.learning.androidroadmap.mvvmPractice.retrofit.UseRetrofit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComponentMvvm : KoinComponent {
    val calculator : Calculator by inject()
    val retrofit2 : UseRetrofit by inject()
}