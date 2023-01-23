package com.learning.androidroadmap.mvvmPractice.applicationclass

import android.app.Application
import com.learning.androidroadmap.mvvmPractice.modules.calculatorModule
import org.koin.core.context.startKoin

class ApplicationMvvm : Application(){
        override fun onCreate(){
            super.onCreate()

            startKoin{
                modules(calculatorModule)
            }
        }
}