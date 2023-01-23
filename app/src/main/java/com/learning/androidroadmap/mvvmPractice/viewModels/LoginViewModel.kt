package com.learning.androidroadmap.mvvmPractice.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.androidroadmap.mvvmPractice.CredsRepo

class LoginViewModel(val context: Context) : ViewModel() {
    private val _alreadyExists = MutableLiveData<Boolean>()
    val alreadyExists : LiveData<Boolean> = _alreadyExists
    private val _correctPassword = MutableLiveData<Boolean>()
    val correctPassword : LiveData<Boolean> = _correctPassword
    fun getDetails(email: String, password : String){
        if(!CredsRepo.getFromSharedPreferences(email,context)){
            _correctPassword.value = password == CredsRepo.checkPwdAndMail(email, context)
        }
        else{
            _alreadyExists.value = false
        }
    }
}