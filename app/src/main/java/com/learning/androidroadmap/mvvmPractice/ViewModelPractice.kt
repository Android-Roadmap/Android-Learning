package com.learning.androidroadmap.mvvmPractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelPractice : ViewModel() {
    var trigger : MutableLiveData<Boolean> = MutableLiveData(false)
    fun getName(name : String) : Int{
        trigger.postValue(true)
        return name.length
    }
}