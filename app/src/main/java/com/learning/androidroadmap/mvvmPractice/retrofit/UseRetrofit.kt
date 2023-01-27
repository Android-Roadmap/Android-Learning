package com.learning.androidroadmap.mvvmPractice.retrofit

import com.learning.androidroadmap.mvvmPractice.interfaces.PlaceHolder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UseRetrofit {
    fun getRetrofit(): PlaceHolder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(PlaceHolder::class.java)
    }
}