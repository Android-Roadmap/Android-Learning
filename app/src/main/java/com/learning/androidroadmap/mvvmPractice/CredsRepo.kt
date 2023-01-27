package com.learning.androidroadmap.mvvmPractice

import android.content.Context
import com.learning.androidroadmap.mvvmPractice.data_class.UserData
import com.learning.androidroadmap.mvvmPractice.databasecomponents.UserDatabase
import com.learning.androidroadmap.mvvmPractice.databasecomponents.UserEntity
import com.learning.androidroadmap.mvvmPractice.interfaces.PlaceHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException

object CredsRepo {
    private lateinit var userDb : UserDatabase
    fun addToSharedPreferences(email: String, password: String, context: Context) {
        val sharedPreferences = getNewEncryptedPreferences(context)
        sharedPreferences.edit().apply {
            putString(email, password)
        }
            .apply()
    }

    fun getFromSharedPreferences(email: String, _context: Context): Boolean {
        val data = getNewEncryptedPreferences(_context).getString(email, null)
        return data.isNullOrEmpty()
    }

    fun checkPwdAndMail(email: String, context: Context): String? {
        return getNewEncryptedPreferences(context).getString(email, null)
    }

    fun addToSavedState(email: String, context: Context) {
        val sharedPreferences = savedLoginInfo(context)
        sharedPreferences.edit().apply {
            putString("SavedCred", email)
        }
            .apply()
    }

    fun checkSavedState(context: Context): String {
        return savedLoginInfo(context).getString("SavedCred", null) ?: "No data"
    }

    fun clearSavedState(context: Context) {
        val sharedPreferences = savedLoginInfo(context)
        sharedPreferences.edit().apply {
            clear()
        }
            .apply()
    }

    suspend fun getDataFromApi(retrofit: PlaceHolder): List<UserData> {
        var data = listOf<UserData>()
        val xyz = CoroutineScope(IO).launch {
            data = try{
                retrofit.getData()
            } catch (e : IOException){
                emptyList()
            }
        }
        xyz.join()
        return data
    }

    fun insertDataToDatabase(result: List<UserData>, context: Context) {
        CoroutineScope(IO).launch{
            userDb= UserDatabase.getDatabase(context)
            for(i in result.indices){
                val data = UserEntity(
                    result[i].id,
                    result[i].title,
                    result[i].body
                )
                userDb.userDao().insert(data)
            }
        }
    }

    suspend fun readDataFromDatabase(context: Context): MutableList<UserData> {
        val result = mutableListOf<UserData>()
       val xyz = CoroutineScope(IO).launch {
            userDb = UserDatabase.getDatabase(context)
            val data = userDb.userDao().getAll()
            for (i in data.indices){
                val data2 = UserData(
                    i.toString(),
                    data[i].id,
                    data[i].title,
                    data[i].body
                )
                result.add(data2)
            }
        }
        xyz.join()
        return result
        }
}