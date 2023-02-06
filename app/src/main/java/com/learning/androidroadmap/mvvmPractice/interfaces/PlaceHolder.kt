package com.learning.androidroadmap.mvvmPractice.interfaces

import com.learning.androidroadmap.mvvmPractice.data_class.PostsData
import retrofit2.http.GET

interface PlaceHolder {
    @GET("/posts")
    suspend fun getData() : List<PostsData>
}