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
    private val _checkSavedState = MutableLiveData<String>()
    val checkStateSaved : LiveData<String> = _checkSavedState

    fun checkSavedState(){
        val result = CredsRepo.checkSavedState(context)
        _checkSavedState.value = result
    }

    fun getDetails(email: String, password : String, checkSave : Boolean){
        if(!CredsRepo.getFromSharedPreferences(email,context)){
            _correctPassword.value = password == CredsRepo.checkPwdAndMail(email, context)
            if(checkSave){
                CredsRepo.addToSavedState(email, context)
            }
        }
        else{
            _alreadyExists.value = false
        }
    }
}