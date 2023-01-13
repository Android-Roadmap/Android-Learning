package com.learning.androidroadmap.mvvmPractice.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    private val _emailValidChecker = MutableLiveData<Boolean>()
    val emailValidChecker : LiveData<Boolean> = _emailValidChecker

    fun checkEmail(email: String) {
        _emailValidChecker.value = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}