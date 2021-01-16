package com.myapp.mymoviesapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.CoroutinesDispatcherProvider
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieHomeViewModel @Inject constructor(private val repository: Repository, private val dispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {


    private val _nowPlayingMoviesLiveData = MutableLiveData<ResultWrapper<MovieSearchResponse>>()

    val nowPlayingMovieLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() = _nowPlayingMoviesLiveData

    private var nowPlayingJob : CompletableJob? = null

    fun getNowPlayingMovies(){

        nowPlayingJob = Job()

        _nowPlayingMoviesLiveData.value = ResultWrapper.Loading(true)

        nowPlayingJob?.let {

            CoroutineScope(dispatcherProvider.io + it).launch {

                val res =  repository.getNowPlayingMovies()

                _nowPlayingMoviesLiveData.postValue(res)

            }

        }

    }

    private val _upcomingMoviesLiveData : MutableLiveData<ResultWrapper<MovieSearchResponse>> = MutableLiveData()

    val upcomingMoviesLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() =  _upcomingMoviesLiveData

    private var upcomingMovieJob : CompletableJob? = null

    fun getUpcomingMovies(){

        upcomingMovieJob = Job()

        _upcomingMoviesLiveData.value = ResultWrapper.Loading(true)

        upcomingMovieJob?.let {

            CoroutineScope(dispatcherProvider.io + it).launch {

                val res = repository.getUpcomingMovies()

                _upcomingMoviesLiveData.postValue(res)

            }
        }
    }

    private val _topRatedMoviesLiveData : MutableLiveData<ResultWrapper<MovieSearchResponse>> = MutableLiveData()

    val topRatedMoviesLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() =  _topRatedMoviesLiveData

    private var topRatedMovieJob : CompletableJob? = null

    fun getTopRatedMovies(){

        topRatedMovieJob = Job()

        _topRatedMoviesLiveData.postValue(ResultWrapper.Loading(true))

        topRatedMovieJob?.let {

            CoroutineScope(dispatcherProvider.io + it).launch {

                val res = repository.getTopRatedMovies()

                _topRatedMoviesLiveData.postValue(res)

            }
        }
    }

    private val _popularMoviesLiveData : MutableLiveData<ResultWrapper<MovieSearchResponse>> = MutableLiveData()

    val popularMoviesLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() =  _popularMoviesLiveData

    private var popularMovieJob : CompletableJob? = null

    fun getPopularMovies(){

        popularMovieJob = Job()

        _popularMoviesLiveData.postValue(ResultWrapper.Loading(true))

        popularMovieJob?.let {

            CoroutineScope(dispatcherProvider.io + it).launch {

                val res = repository.getPopularMovies()

                _popularMoviesLiveData.postValue(res)

            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        nowPlayingJob?.cancel()
        upcomingMovieJob?.cancel()
        topRatedMovieJob?.cancel()
        popularMovieJob?.cancel()
    }
}