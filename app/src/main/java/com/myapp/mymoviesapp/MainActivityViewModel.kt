package com.myapp.mymoviesapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repo : Repository)  : ViewModel(){

    private val _nowPlayingMoviesLiveData = MutableLiveData<ResultWrapper<MovieSearchResponse>>()

    val nowPlayingMovieLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() = _nowPlayingMoviesLiveData

    private var nowPlayingJob : CompletableJob? = null

    fun getNowPlayingMovies(){

        nowPlayingJob = Job()

        nowPlayingJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _nowPlayingMoviesLiveData.postValue(ResultWrapper.Loading(true))

                val res =  repo.getNowPlayingMovies()

                Log.d("DDDDD","res = $res")

                _nowPlayingMoviesLiveData.postValue(res)

            }

        }

    }


    override fun onCleared() {
        super.onCleared()
        nowPlayingJob?.cancel()

    }

}