package com.myapp.mymoviesapp.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import kotlinx.coroutines.*

class SearchMoviesViewModel(private val repo : Repository) : ViewModel() {

    private var jobSearchMovie : CompletableJob? = null

    private val _liveData = MutableLiveData<ResultWrapper<MovieSearchResponse>>()

    val  liveData : LiveData<ResultWrapper<MovieSearchResponse>> get() = _liveData

    fun loadMoviesNextPage(query : String, page : Int) =  liveData(context = CoroutineScope(Dispatchers.IO).coroutineContext) {

        Log.d("SSSSSSSS", "Repository searchMovies q = " + query)

        emit(ResultWrapper.Loading(boolean = true))

        val response = RemoteDataSource(ApiClient.apiService).searchMoviesWithPageNumber(query, page)

        emit(response)

    }


    fun searchMovies( name :String) {

        jobSearchMovie = Job()

        jobSearchMovie?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _liveData.postValue(ResultWrapper.Loading(true))

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