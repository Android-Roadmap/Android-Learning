package com.learning.androidroadmap

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    var fullName : String,
    var uid : String,
    var parentName : String,
    var phoneNum : String,
    var postalCode : String
): Parcelable
