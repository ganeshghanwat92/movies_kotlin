package com.myapp.mymoviesapp.repository

import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(val remoteDataSource: RemoteDataSource) {

   suspend fun searchMovies(query: String)  = remoteDataSource.searchMovies(query)

   suspend fun searchMoviesWithPageNumber(query: String, page : Int)  = remoteDataSource.searchMoviesWithPageNumber(query, page)

   suspend fun searchTVShows(query: String)  = remoteDataSource.searchTVShows(query)

   suspend fun getMovieDetails(id : Int)  = remoteDataSource.getMovieDetails(id)

   suspend fun getTVShowDetails(id : Int)  = remoteDataSource.getTVShowDetails(id)

   suspend fun getPopularMovies()  = remoteDataSource.getPopularMovies()

   suspend fun getTopRatedMovies()  = remoteDataSource.getTopRatedMovies()

   suspend fun getUpcomingMovies()  = remoteDataSource.getUpcomingMovies()

   suspend fun getNowPlayingMovies()  = remoteDataSource.getNowPlayingMovies()

   suspend fun getTopRatedTVShows()  = remoteDataSource.getTopRatedTVShows()

   suspend fun getPopularTVShows()  = remoteDataSource.getPopularTVShows()

   suspend fun getOnAirTVShows()  = remoteDataSource.getOnAirTVShows()

   suspend fun multiSearch(query: String)  = remoteDataSource.multiSearch(query)

   suspend fun getPeopleDetails(id: Int)  = remoteDataSource.getPeopleDetails(id)

}