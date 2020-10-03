package com.myapp.mymoviesapp.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class TvShowSearchViewModel @Inject constructor(private val repo : Repository) : ViewModel() {

    private var jobSearchTVShow : CompletableJob? = null

    private val _tvShowsLiveData  = MutableLiveData<ResultWrapper<TVSearchResponse>>()

    val tvShowsLiveData : LiveData<ResultWrapper<TVSearchResponse>> get() = _tvShowsLiveData

    fun searchTVShows(name: String){

        jobSearchTVShow = Job()

        _tvShowsLiveData.postValue(ResultWrapper.Loading(true))

        jobSearchTVShow?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _tvShowsLiveData.postValue(ResultWrapper.Loading(true))

                val res = repo.searchTVShows(name)

                 _tvShowsLiveData.postValue(res)

            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        jobSearchTVShow?.cancel()
    }

}