package com.learning.androidroadmap.mvvmPractice.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.androidroadmap.mvvmPractice.CredsRepo
import com.learning.androidroadmap.mvvmPractice.data_class.PostsData
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ContentDataViewModel : ViewModel(), KoinComponent {
    private var result = listOf<PostsData>()
    private var _data = MutableLiveData<List<PostsData>>()
    var data: LiveData<List<PostsData>> = _data
    private var _statusCode = MutableLiveData<Int>()
    var statusCode: LiveData<Int> = _statusCode
    private var _statusCodeNotFound = MutableLiveData<Int>()
    var statusCodeNotFound: LiveData<Int> = _statusCodeNotFound
    private var _cache = MutableLiveData<List<PostsData>>()
    var cache: LiveData<List<PostsData>> = _cache

    fun getDataFromRepository() {
        viewModelScope.launch {
            result = CredsRepo.getDataFromApi()
            if (result.isEmpty()){
                _statusCodeNotFound.value = NOT_FOUND
            }else {
                _data.value = result
                _statusCode.value = SUCCESS
                //push data to database
                CredsRepo.insertDataToDatabase(result)
            }
        }
    }

    fun checkForCache(){
        viewModelScope.launch {
            val result = CredsRepo.readDataFromDatabase()
            if(result.isNotEmpty()){
                _cache.value = result
            }
        }
    }

    fun logout(){
        CredsRepo.clearSavedState()
    }

    companion object {
        const val SUCCESS = 200
        const val NOT_FOUND = 404
    }

}