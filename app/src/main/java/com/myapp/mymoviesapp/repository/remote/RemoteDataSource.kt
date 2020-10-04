package com.myapp.mymoviesapp.repository.remote

import com.myapp.mymoviesapp.Constants
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService, val baseUrlInterceptor: BaseUrlInterceptor) : BaseDataSource() {

     val API_KEY : String = "5b43064688877f072a019cc2c3a8c632";

    suspend fun searchMovies(query : String) = getResult {
        // change base url using okhttp interceptor
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.searchMovies(API_KEY, query);
    }

    suspend fun searchMoviesWithPageNumber(query : String,page : Int) = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.searchMoviesWithPageNumber(API_KEY, query, page);
    }

    suspend fun searchMoviesNew(query : String)  = apiService.searchMovies(API_KEY,query);

    suspend fun searchTVShows(query : String) = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.searchTvShows(API_KEY, query);
    }

    suspend fun getMovieDetails(id : Int) = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getMovieDetails(id,API_KEY);
    }

    suspend fun getTVShowDetails(id : Int) = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getTVShowDetails(id,API_KEY);
    }

     suspend fun getPopularMovies() = getResult {
         baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getPopularMovies(API_KEY);
    }

    suspend fun getTopRatedMovies() = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getTopRatedMovies(API_KEY);
    }
     suspend fun getUpcomingMovies() = getResult {
         baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getUpcomingMovies(API_KEY);
    }
    suspend fun getNowPlayingMovies() = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getNowPlayingMovies(API_KEY);
    }
     suspend fun getTopRatedTVShows() = getResult {
         baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getTopRatedTVShows(API_KEY);
    }
    suspend fun getPopularTVShows() = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getPopularTVShows(API_KEY);
    }
    suspend fun getOnAirTVShows() = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getOnAirTVShows(API_KEY);
    }
    suspend fun multiSearch(query: String) = getResult {
        baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.multiSearch(API_KEY,query);
    }
     suspend fun getPeopleDetails(id: Int) = getResult {
         baseUrlInterceptor.setHost(Constants.baseUrl)
        apiService.getPeopleDetails(id, API_KEY);
    }

}