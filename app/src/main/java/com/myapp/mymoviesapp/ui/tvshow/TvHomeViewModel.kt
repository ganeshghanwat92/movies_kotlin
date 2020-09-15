package com.myapp.mymoviesapp.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*

class TvHomeViewModel(private val repository: Repository) : ViewModel() {

    private val _onAirLiveData = MutableLiveData<ResultWrapper<TVSearchResponse>>()

    val onAirLiveData : LiveData<ResultWrapper<TVSearchResponse>> get() = _onAirLiveData

    private var onAirJob : CompletableJob? = null

    fun getOnAirTVShows(){

        onAirJob = Job()

        onAirJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _onAirLiveData.postValue(ResultWrapper.Loading(true))

                val res =  repository.getOnAirTVShows()

                _onAirLiveData.postValue(res)

            }

        }

    }

    private val _topRatedLiveData : MutableLiveData<ResultWrapper<TVSearchResponse>> = MutableLiveData()

    val topRatedLiveData : LiveData<ResultWrapper<TVSearchResponse>> get() =  _topRatedLiveData

    private var topRatedJob : CompletableJob? = null

    fun getTopRated(){

        topRatedJob = Job()

        topRatedJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _topRatedLiveData.postValue(ResultWrapper.Loading(true))

                val res = repository.getTopRatedTVShows()

                _topRatedLiveData.postValue(res)

            }
        }
    }

    private val _popularLiveData : MutableLiveData<ResultWrapper<TVSearchResponse>> = MutableLiveData()

    val popularLiveData : LiveData<ResultWrapper<TVSearchResponse>> get() =  _popularLiveData

    private var popularJob : CompletableJob? = null

    fun getPopular(){

        popularJob = Job()

        popularJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _popularLiveData.postValue(ResultWrapper.Loading(true))

                val res = repository.getPopularTVShows()

                _popularLiveData.postValue(res)

            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        onAirJob?.cancel()
        topRatedJob?.cancel()
        popularJob?.cancel()
    }

}