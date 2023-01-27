package com.learning.androidroadmap.mvvmPractice.data_class

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("userId")
    val userId : String,
    @SerializedName("id")
    val id : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("body")
    val body : String
)
