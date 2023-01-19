package com.learning.androidroadmap.mvvmPractice

import android.content.Context
import com.learning.androidroadmap.navcomponent.getEncryptedPreferences

object CredsRepo {
    fun addToSharedPreferences(email: String, password: String, context: Context) {
        val sharedPreferences = getEncryptedPreferences(context)
        sharedPreferences.edit().apply{
            putString(email,password)
        }
            .apply()

    }
    fun getFromSharedPreferences(email: String, _context: Context) : Boolean {
        val data = getEncryptedPreferences(_context).getString(email, null)
        println("Suwa eyat : ${data.isNullOrEmpty()}")
        return data.isNullOrEmpty()
    }

    fun checkPwdAndMail(email: String, context: Context): String? {
        println("Suwa eyat ako : ${getEncryptedPreferences(context).getString(email, null)}")
        return getEncryptedPreferences(context).getString(email, null)
    }

}