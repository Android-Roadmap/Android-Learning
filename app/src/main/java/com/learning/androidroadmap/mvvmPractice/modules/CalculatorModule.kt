package com.learning.androidroadmap.mvvmPractice.modules

import com.learning.androidroadmap.mvvmPractice.Calculator
import com.learning.androidroadmap.mvvmPractice.retrofit.UseRetrofit
import org.koin.dsl.module

val calculatorModule = module {
    single { Calculator() }
}

val retrofitModule = module {
    single { UseRetrofit() }
}