package com.learning.androidroadmap.mvvmPractice.applicationclass

import com.learning.androidroadmap.mvvmPractice.Calculator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComponentMvvm : KoinComponent {
    val calculator : Calculator by inject()
}