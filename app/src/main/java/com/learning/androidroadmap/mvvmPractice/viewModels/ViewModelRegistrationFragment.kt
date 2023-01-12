package com.learning.androidroadmap.mvvmPractice.viewModels

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.androidroadmap.mvvmPractice.RepositoryPractice

class ViewModelRegistrationFragment : ViewModel() {
    private var emailValidChecker = MutableLiveData<Boolean>()
    var _emailValidChecker : LiveData<Boolean> = emailValidChecker
    private var passwordEmptyCheck = MutableLiveData<Boolean>()
    var _passwordEmptyCheck : LiveData<Boolean> = passwordEmptyCheck
    private var passwordLengthCheck = MutableLiveData<Boolean>()
    var _passwordLengthCheck : LiveData<Boolean> = passwordLengthCheck
    private var passwordLowerCaseCheck = MutableLiveData<Boolean>()
    var _passwordLowerCaseCheck : LiveData<Boolean> = passwordLowerCaseCheck
    private var passwordUpperCaseCheck = MutableLiveData<Boolean>()
    var _passwordUpperCaseCheck : LiveData<Boolean> = passwordUpperCaseCheck
    private var passwordNumberCheck = MutableLiveData<Boolean>()
    var _passwordNumberCheck : LiveData<Boolean> = passwordNumberCheck
    private var passwordSpecialCharacterCheck = MutableLiveData<Boolean>()
    var _passwordSpecialCharacterCheck : LiveData<Boolean> = passwordSpecialCharacterCheck
    private var passwordMatchCheck = MutableLiveData<Boolean>()
    var _passwordMatchCheck : LiveData<Boolean> = passwordMatchCheck
    private var emailAlreadyExists = MutableLiveData<Boolean>()
    var _emailAlreadyExists : LiveData<Boolean> = emailAlreadyExists

    private var canInsert = MutableLiveData<Boolean>()
    var CanInsert : LiveData<Boolean> = canInsert

    private lateinit var _context: Context

    fun setContext(context: Context){
        _context = context
    }

    fun getCredentials(email : String, password : String, confirmPassword : String){
        val emailCheck = checkEmail(email)
        val passwordCheck = checkPassword(password, confirmPassword)
        if(passwordCheck){
            if(checkIfEmailAlreadyExists(email)){
                canInsert.postValue(true)
                RepositoryPractice.addToSharedPreferences(email, password, _context)
            }
            else
            {
                emailAlreadyExists.postValue(true)
            }
        }
    }

    private fun checkIfEmailAlreadyExists(email: String) : Boolean {
        return if(email.isNullOrEmpty())
            false
        else
            RepositoryPractice.getFromSharedPreferences(email, _context)
    }

    fun checkEmail(email: String) {
        emailValidChecker.value = !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPassword(password: String, confirmPassword: String): Boolean {
        var alphabetUpperCaseCheck = false
        var alphabetLowerCaseCheck = false
        var numberCheck = false
        var specialCharacterCheck = false
        if (password.isNotEmpty()){
            if(password.length>=8){

                for (ch in password){
                    if("ABCDEFGHIJKLMNOPQRSTUVWXYZ".lowercase().contains(ch,false)){
                        alphabetLowerCaseCheck = true
                    }
                    if("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(ch,false)){
                        alphabetUpperCaseCheck = true
                    }
                    if("12343567890".contains(ch, true)){
                        numberCheck = true
                    }
                    if("!@#$%&*?".contains(ch,true)){
                        specialCharacterCheck = true
                    }
                }
                if (alphabetLowerCaseCheck){
                    if(alphabetUpperCaseCheck){
                        if (numberCheck){
                            if (specialCharacterCheck){
                                if (password == confirmPassword){
                                    return true
                                }
                                else{
                                    passwordMatchCheck.postValue(true)
                                }
                            }
                            else
                            {
                                passwordSpecialCharacterCheck.postValue(true)
                            }
                        }
                        else{
                            passwordNumberCheck.postValue(true)
                        }
                    }
                    else
                    {
                        passwordUpperCaseCheck.postValue(true)
                    }
                }
                else{
                    passwordLowerCaseCheck.postValue(true)
                }
            }
            else{
                passwordLengthCheck.postValue(true)
            }
        }
        else{
            passwordEmptyCheck.postValue(true)
        }
        return false
    }

    fun validateEmail(email: String) {

    }
}