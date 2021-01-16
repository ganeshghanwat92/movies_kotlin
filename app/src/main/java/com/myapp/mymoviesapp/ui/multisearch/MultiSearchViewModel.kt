package com.myapp.mymoviesapp.ui.multisearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.CoroutinesDispatcherProvider
import com.myapp.mymoviesapp.datamodel.multisearch.MultiSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MultiSearchViewModel @Inject constructor(private val repository: Repository, private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {

    private var jobMultiSearch : CompletableJob? = null

    private val _multiSearchLiveData  = MutableLiveData<ResultWrapper<MultiSearchResponse>>()

    val multiSearchLiveData : LiveData<ResultWrapper<MultiSearchResponse>> get() = _multiSearchLiveData

    fun multiSearch(query: String){

        jobMultiSearch = Job()

        _multiSearchLiveData.postValue(ResultWrapper.Loading(true))

        jobMultiSearch?.let {

            CoroutineScope(coroutinesDispatcherProvider.io + it).launch {

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