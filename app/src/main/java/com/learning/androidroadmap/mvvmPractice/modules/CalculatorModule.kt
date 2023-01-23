package com.learning.androidroadmap.mvvmPractice.modules

import com.learning.androidroadmap.mvvmPractice.Calculator
import org.koin.dsl.module

val calculatorModule = module {
    single { Calculator() }
}