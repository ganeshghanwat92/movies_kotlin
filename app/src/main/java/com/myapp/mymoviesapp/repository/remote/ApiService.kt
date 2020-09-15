package com.myapp.mymoviesapp.repository.remote

import com.myapp.mymoviesapp.datamodel.movie.MovieDetails
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.datamodel.multisearch.MultiSearchResponse
import com.myapp.mymoviesapp.datamodel.people.Person
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.datamodel.tv.TVShowDetails
import com.myapp.mymoviesapp.repository.ResultWrapper
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/search/movie")
     suspend fun searchMovies(
        @Query("api_key")apiKey : String,
        @Query("query")name : String) : Response<MovieSearchResponse>

    @GET("/3/search/movie")
    suspend fun searchMoviesWithPageNumber(
        @Query("api_key")apiKey : String,
        @Query("query")name : String,
        @Query("page")pageNO : Int) : Response<MovieSearchResponse>



    @GET("/3/search/tv")
    suspend fun searchTvShows(
        @Query("api_key")apiKey : String,
        @Query("query")name : String
    ) : Response<TVSearchResponse>


    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")movie_id : Int,
        @Query("api_key")apiKey : String
    ) : Response<MovieDetails>

    @GET("/3/tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id")tv_id : Int,
        @Query("api_key")apiKey : String
    ) : Response<TVShowDetails>


    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")apiKey: String
    ) : Response<MovieSearchResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")apiKey: String
    ) :Response<MovieSearchResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")apiKey: String
    ) : Response<MovieSearchResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")apiKey: String
    ) : Response<MovieSearchResponse>

    @GET("/3/tv/top_rated")
    suspend fun getTopRatedTVShows(
        @Query("api_key")apiKey: String
    ) : Response<TVSearchResponse>

    @GET("/3/tv/popular")
    suspend fun getPopularTVShows(
        @Query("api_key")apiKey: String
    ) : Response<TVSearchResponse>

  @GET("/3/tv/on_the_air")
    suspend fun getOnAirTVShows(
        @Query("api_key")apiKey: String
    ) : Response<TVSearchResponse>

  @GET("/3/search/multi")
    suspend fun multiSearch(
        @Query("api_key")apiKey: String,
        @Query("query")query: String
    ) : Response<MultiSearchResponse>

  @GET("/3/person/{id}")
    suspend fun getPeopleDetails(
        @Path("id")id: Int,
        @Query("api_key")apiKey: String
    ) : Response<Person>



}