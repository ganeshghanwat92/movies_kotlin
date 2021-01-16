package com.myapp.mymoviesapp.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.CoroutinesDispatcherProvider
import com.myapp.mymoviesapp.datamodel.movie.MovieDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(val repo : Repository, val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {

    private var jobMovieDetail : CompletableJob? = null

    private val _liveDataMovie = MutableLiveData<ResultWrapper<MovieDetails>>()

    val liveDataMovie : LiveData<ResultWrapper<MovieDetails>> get()  = _liveDataMovie

    fun getMovieDetails(id : Int){

        Log.d("SSSS","getMovieDetails viewmodel id $id")

        jobMovieDetail = Job()

        _liveDataMovie.value = ResultWrapper.Loading(true)

        jobMovieDetail?.let {

            CoroutineScope(coroutinesDispatcherProvider.io + it).launch {

                Log.d("SSSS","scope = "+this.coroutineContext)
                Log.d("SSSS","Inside Global Scope "+Thread.currentThread().name.toString())


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