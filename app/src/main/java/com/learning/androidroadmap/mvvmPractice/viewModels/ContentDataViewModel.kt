package com.learning.androidroadmap.mvvmPractice.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.androidroadmap.mvvmPractice.CredsRepo
import com.learning.androidroadmap.mvvmPractice.data_class.PostsData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ContentDataViewModel : ViewModel(), KoinComponent {
    private var _data = MutableLiveData<List<PostsData>>()
    var data: LiveData<List<PostsData>> = _data
    private var _statusCode = MutableLiveData<Int>()
    var statusCode: LiveData<Int> = _statusCode
    private var _statusCodeNotFound = MutableLiveData<Int>()
    var statusCodeNotFound: LiveData<Int> = _statusCodeNotFound
    private var _cache = MutableLiveData<List<PostsData>>()
    var cache: LiveData<List<PostsData>> = _cache

    fun getDataFromAPIViaFlow(){
        viewModelScope.launch {
            CredsRepo.fetchDataFromApi()
                .catch {
                    _statusCodeNotFound.value = NOT_FOUND
                }
                .collect{
                    _data.value = it
                    _statusCode.value = SUCCESS
                    CredsRepo.insertDataToDatabase(it)
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