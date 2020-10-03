package com.myapp.mymoviesapp.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.movie.MovieDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(val repo : Repository) : ViewModel() {

    private var jobMovieDetail : CompletableJob? = null

    private val _liveDataMovie = MutableLiveData<ResultWrapper<MovieDetails>>()

    val liveDataMovie : LiveData<ResultWrapper<MovieDetails>> get()  = _liveDataMovie

    fun getMovieDetails(id : Int){

        Log.d("SSSS","getMovieDetails viewmodel id $id")

        jobMovieDetail = Job()

        jobMovieDetail?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _liveDataMovie.postValue(ResultWrapper.Loading(true))

                 val res = repo.getMovieDetails(id)

                Log.d("SSSS","getMovieDetails viewmodel res = $res")

                _liveDataMovie.postValue(res)

            }

        }

    }

    override fun onCleared() {
        super.onCleared()

        jobMovieDetail?.cancel()
    }


}