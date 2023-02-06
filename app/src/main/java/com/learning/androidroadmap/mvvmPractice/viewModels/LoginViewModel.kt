package com.learning.androidroadmap.mvvmPractice.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.androidroadmap.mvvmPractice.CredsRepo

class LoginViewModel : ViewModel() {
    private val _alreadyExists = MutableLiveData<Boolean>()
    val alreadyExists: LiveData<Boolean> = _alreadyExists
    private val _loginState = MutableLiveData(false)
    val loginState: LiveData<Boolean> = _loginState

    fun getDetails(email: String, password: String, checkSave: Boolean) {
        if(CredsRepo.getCredential(email)?.isNotEmpty() == true){
            if (CredsRepo.getCredential(email)?.equals(password) == true) {
                _loginState.value = true
                if (checkSave)
                    CredsRepo.addToSavedState(true)
            } else {
                _loginState.value = false
            }
        }else{
            _alreadyExists.value = false
        }
    }
}