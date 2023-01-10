package com.learning.androidroadmap.navcomponent

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

fun getEncryptedPreferences(context : Context): SharedPreferences {
    val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val sharedPref = EncryptedSharedPreferences.create( "userCredentials", masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    return  sharedPref
}