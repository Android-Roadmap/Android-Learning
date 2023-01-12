package com.learning.androidroadmap.mvvmPractice

import android.content.Context
import com.learning.androidroadmap.navcomponent.getEncryptedPreferences

object RepositoryPractice {
    fun addToSharedPreferences(email: String, password: String, _context: Context) {
        val sharedPreferences = getEncryptedPreferences(_context)
        sharedPreferences.edit().apply{
            putString(email,password)
        }
            .apply()

    }
    fun getFromSharedPreferences(email: String, _context: Context) : Boolean {
        val data = getEncryptedPreferences(_context).getString(email, null)
        return data.isNullOrEmpty()
    }

}