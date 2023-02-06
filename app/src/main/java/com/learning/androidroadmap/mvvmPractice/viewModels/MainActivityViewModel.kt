package com.learning.androidroadmap.mvvmPractice.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.androidroadmap.mvvmPractice.CredsRepo

class MainActivityViewModel : ViewModel() {
    private val _loginState = MutableLiveData<Boolean>()
    val loginState:LiveData<Boolean> = _loginState

    fun checkLoginState(){
        _loginState.value = CredsRepo.checkSavedState()
    }
}