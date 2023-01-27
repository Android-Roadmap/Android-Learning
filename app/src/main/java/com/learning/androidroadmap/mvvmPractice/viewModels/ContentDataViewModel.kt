package com.learning.androidroadmap.mvvmPractice.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.androidroadmap.mvvmPractice.CredsRepo
import com.learning.androidroadmap.mvvmPractice.applicationclass.ComponentMvvm
import com.learning.androidroadmap.mvvmPractice.data_class.UserData
import kotlinx.coroutines.launch

class ContentDataViewModel(val context:Context) : ViewModel() {
    private var result = listOf<UserData>()
    private var _data = MutableLiveData<List<UserData>>()
    var data: LiveData<List<UserData>> = _data
    private var _statusCode = MutableLiveData<Int>()
    var statusCode: LiveData<Int> = _statusCode
    private var _cache = MutableLiveData<List<UserData>>()
    var cache: LiveData<List<UserData>> = _cache
    private var componentMvvm = ComponentMvvm()

    fun getDataFromRepository() {
        val retrofit = componentMvvm.retrofit2.getRetrofit()
        viewModelScope.launch {
            result = CredsRepo.getDataFromApi(retrofit)
            if (result.isEmpty()){
                _statusCode.value = NOT_FOUND
            }else {
                _data.value = result
                _statusCode.value = SUCCESS
                //push data to database
                CredsRepo.insertDataToDatabase(result, context)
            }
        }
    }

    fun checkForCache(){
        viewModelScope.launch {
            val result = CredsRepo.readDataFromDatabase(context)
            if(result.isNotEmpty()){
                _cache.value = result
            }
        }
    }

    companion object {
        const val SUCCESS = 200
        const val NOT_FOUND = 404
    }

}