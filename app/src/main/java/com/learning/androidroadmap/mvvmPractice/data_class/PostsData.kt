package com.learning.androidroadmap.mvvmPractice.data_class

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsData(
    @SerializedName("userId")
    val userId : String,
    @SerializedName("id")
    val id : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("body")
    val body : String
):Parcelable
