package com.myapp.mymoviesapp.ui.multisearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.multisearch.MultiSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*

class MultiSearchViewModel(private val repository: Repository) : ViewModel() {

    private var jobMultiSearch : CompletableJob? = null

    private val _multiSearchLiveData  = MutableLiveData<ResultWrapper<MultiSearchResponse>>()

    val multiSearchLiveData : LiveData<ResultWrapper<MultiSearchResponse>> get() = _multiSearchLiveData

    fun multiSearch(query: String){

        jobMultiSearch = Job()

        _multiSearchLiveData.postValue(ResultWrapper.Loading(true))

        jobMultiSearch?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _multiSearchLiveData.postValue(ResultWrapper.Loading(true))

                val res = repository.multiSearch(query)

                _multiSearchLiveData.postValue(res)

            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        jobMultiSearch?.cancel()
    }

}