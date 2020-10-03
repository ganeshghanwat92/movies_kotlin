package com.myapp.mymoviesapp.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.tv.TVShowDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class TVShowDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var jobTVShowDetails : CompletableJob? = null

    private val _tvShowLiveData  = MutableLiveData<ResultWrapper<TVShowDetails>>()

    val tvShowsLiveData : LiveData<ResultWrapper<TVShowDetails>> get() = _tvShowLiveData

    fun getTVShowsDetails(id: Int){

        jobTVShowDetails = Job()

        _tvShowLiveData.postValue(ResultWrapper.Loading(true))

        jobTVShowDetails?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _tvShowLiveData.postValue(ResultWrapper.Loading(true))

                val res = repository.getTVShowDetails(id)

                _tvShowLiveData.postValue(res)

            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        jobTVShowDetails?.cancel()
    }


}