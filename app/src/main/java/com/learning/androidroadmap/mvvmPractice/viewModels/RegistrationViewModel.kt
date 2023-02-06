package com.learning.androidroadmap.mvvmPractice.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.androidroadmap.mvvmPractice.CredsRepo
import java.util.regex.Pattern

class RegistrationViewModel : ViewModel() {
    private var pwd = ""
    private val _emailValidChecker = MutableLiveData<String>()
    val emailValidChecker : LiveData<String> = _emailValidChecker
    private val _passwordChecker = MutableLiveData<String>()
    val passwordChecker : LiveData<String> = _passwordChecker
    private val _confirmPasswordChecker = MutableLiveData<String>()
    val confirmPasswordChecker : LiveData<String> = _confirmPasswordChecker
    private val _switchFragment = MutableLiveData<Boolean>()
    val switchFragment : LiveData<Boolean> = _switchFragment

    fun checkEmailIsValid(email: String) {
        val result = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if(result){
            _emailValidChecker.value = "Valid"
        }else
        {
            _emailValidChecker.value = "Invalid"
        }
    }

    fun checkPasswordIsValid(password : String){
        pwd = password
        val regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$"
        val p = Pattern.compile(regex)
        val m = p.matcher(password)
        val result = m.matches()
        if (password.length>=8 && result){
            _passwordChecker.value = "Valid"
        }else{
            _passwordChecker.value = "Invalid"
        }
    }

    fun checkPasswordMatches(confirmPassword : String){
        if(confirmPassword==pwd){
            _confirmPasswordChecker.value = "Valid"
        }else{
            _confirmPasswordChecker.value = "Invalid"
        }
    }

    fun checkForEmpty(email: String, password: String, password2: String){
        if (email.isEmpty()){
            _emailValidChecker.value = "Empty"
        }
        if (password.isEmpty()){
            _passwordChecker.value = "Empty"
        }
        if (password2.isEmpty()){
            _confirmPasswordChecker.value = "Empty"
        }
        if (_emailValidChecker.value == "Valid" && _passwordChecker.value == "Valid" && _confirmPasswordChecker.value == "Valid"){
            if(CredsRepo.getCredential(email).isNullOrEmpty()){
                CredsRepo.registerUser(email, password)
                CredsRepo.clearSavedState()
                _switchFragment.value = true
            }else{
                _emailValidChecker.value = "Exists"
            }
        }
    }
}