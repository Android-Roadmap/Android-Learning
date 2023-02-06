package com.learning.androidroadmap.mvvmPractice.applicationclass

import android.app.Application
import com.learning.androidroadmap.mvvmPractice.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationMvvm : Application(){
        override fun onCreate(){
            super.onCreate()

            startKoin{
                androidContext(this@ApplicationMvvm)
                androidLogger()
                modules(
                    calculatorModule,
                    retrofitModule,
                    dbModule,
                    sharedPreferencesModule,
                    getDataFromSharedPreferencesModule,
                    getRetrofit
                )
            }
        }
}