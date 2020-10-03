package com.myapp.mymoviesapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieHomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _nowPlayingMoviesLiveData = MutableLiveData<ResultWrapper<MovieSearchResponse>>()

    val nowPlayingMovieLiveData : LiveData<ResultWrapper<MovieSearchResponse>> get() = _nowPlayingMoviesLiveData

    private var nowPlayingJob : CompletableJob? = null

    fun getNowPlayingMovies(){

        nowPlayingJob = Job()

        nowPlayingJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _nowPlayingMoviesLiveData.postValue(ResultWrapper.Loading(true))

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

        upcomingMovieJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _upcomingMoviesLiveData.postValue(ResultWrapper.Loading(true))

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

        topRatedMovieJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _topRatedMoviesLiveData.postValue(ResultWrapper.Loading(true))

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

        popularMovieJob?.let {

            CoroutineScope(Dispatchers.IO + it).launch {

                _popularMoviesLiveData.postValue(ResultWrapper.Loading(true))

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