package com.myapp.mymoviesapp.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.myapp.mymoviesapp.CoroutinesDispatcherProvider
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMoviesViewModel @Inject constructor(private val repo : Repository, private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {

    private var jobSearchMovie : CompletableJob? = null

    private val _liveData = MutableLiveData<ResultWrapper<MovieSearchResponse>>()

    val  liveData : LiveData<ResultWrapper<MovieSearchResponse>> get() = _liveData

    fun loadMoviesNextPage(query : String, page : Int) =  liveData(context = CoroutineScope(coroutinesDispatcherProvider.io).coroutineContext) {

        Log.d("SSSSSSSS", "Repository searchMovies q = " + query)

        emit(ResultWrapper.Loading(boolean = true))

        val response = repo.searchMoviesWithPageNumber(query, page)

        emit(response)

    }


    fun searchMovies( name :String) {

        jobSearchMovie = Job()

        _liveData.postValue(ResultWrapper.Loading(true))

        jobSearchMovie?.let {

            CoroutineScope(coroutinesDispatcherProvider.io + it).launch {

                    val res = repo.searchMovies(name)

                    _liveData.postValue(res)

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobSearchMovie?.cancel()
    }

}