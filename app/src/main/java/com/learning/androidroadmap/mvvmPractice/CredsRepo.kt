package com.learning.androidroadmap.mvvmPractice

import android.content.SharedPreferences
import com.learning.androidroadmap.mvvmPractice.data_class.PostsData
import com.learning.androidroadmap.mvvmPractice.databasecomponents.UserDatabase
import com.learning.androidroadmap.mvvmPractice.databasecomponents.UserEntity
import com.learning.androidroadmap.mvvmPractice.interfaces.PlaceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.Flow

object CredsRepo: KoinComponent {
    private val retrofit : Retrofit by inject()
    private val userDb : UserDatabase by inject()
    private val userCredPref : SharedPreferences by inject(named(USER_CRED_PREF))
    private val preferencesEditor = userCredPref.edit()
    private val loginPreference : SharedPreferences by inject(named(LOGIN_PREF))
    private val placeHolder: PlaceHolder = retrofit.create(PlaceHolder::class.java)

    fun registerUser(email: String, password: String) {
        preferencesEditor.apply {
            putString(email, password)
        }.apply()
    }

    fun getCredential(email: String): String? {
        return  userCredPref.getString(email, null)
    }

    fun addToSavedState(hasRememberMe: Boolean) {
        loginPreference.edit().apply {
            putBoolean(LOGIN_STATE, hasRememberMe)
        }.apply()
    }

    fun checkSavedState(): Boolean {
        return loginPreference.getBoolean(LOGIN_STATE, false)
    }

    fun clearSavedState() {
        loginPreference.edit().apply {
            putBoolean(LOGIN_STATE, false)
        }.apply()
    }

    fun insertDataToDatabase(result: List<PostsData>) {
        CoroutineScope(IO).launch{
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

    suspend fun readDataFromDatabase(): MutableList<PostsData> {
        val result = mutableListOf<PostsData>()
       val job = CoroutineScope(IO).launch {
            val data = userDb.userDao().getAll()
            for (i in data.indices){
                val data2 = PostsData(
                    i.toString(),
                    data[i].id,
                    data[i].title,
                    data[i].body
                )
                result.add(data2)
            }
        }
        job.join()
        return result
        }

    fun fetchDataFromApi() = flow {
        val response = placeHolder.getData()
        emit(response)
    }.flowOn(Dispatchers.IO)
}