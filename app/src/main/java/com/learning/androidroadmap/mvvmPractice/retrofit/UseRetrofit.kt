package com.learning.androidroadmap.mvvmPractice.retrofit

import android.content.Context
import com.learning.androidroadmap.R
import com.learning.androidroadmap.mvvmPractice.interfaces.PlaceHolder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UseRetrofit {
    fun getRetrofit(): PlaceHolder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PlaceHolder::class.java)
    }
    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}