package com.myapp.mymoviesapp.repository.remote

class RemoteDataSource (val apiService: ApiService) : BaseDataSource() {

     val API_KEY : String = "5b43064688877f072a019cc2c3a8c632";

    suspend fun searchMovies(query : String) = getResult {
        apiService.searchMovies(API_KEY, query);
    }

    suspend fun searchMoviesWithPageNumber(query : String,page : Int) = getResult {
        apiService.searchMoviesWithPageNumber(API_KEY, query, page);
    }

    suspend fun searchMoviesNew(query : String)  = apiService.searchMovies(API_KEY,query);

    suspend fun searchTVShows(query : String) = getResult {
        apiService.searchTvShows(API_KEY, query);
    }

    suspend fun getMovieDetails(id : Int) = getResult {
        apiService.getMovieDetails(id,API_KEY);
    }

    suspend fun getTVShowDetails(id : Int) = getResult {
        apiService.getTVShowDetails(id,API_KEY);
    }

     suspend fun getPopularMovies() = getResult {
        apiService.getPopularMovies(API_KEY);
    }

    suspend fun getTopRatedMovies() = getResult {
        apiService.getTopRatedMovies(API_KEY);
    }
     suspend fun getUpcomingMovies() = getResult {
        apiService.getUpcomingMovies(API_KEY);
    }
    suspend fun getNowPlayingMovies() = getResult {
        apiService.getNowPlayingMovies(API_KEY);
    }
     suspend fun getTopRatedTVShows() = getResult {
        apiService.getTopRatedTVShows(API_KEY);
    }
    suspend fun getPopularTVShows() = getResult {
        apiService.getPopularTVShows(API_KEY);
    }
    suspend fun getOnAirTVShows() = getResult {
        apiService.getOnAirTVShows(API_KEY);
    }
    suspend fun multiSearch(query: String) = getResult {
        apiService.multiSearch(API_KEY,query);
    }
     suspend fun getPeopleDetails(id: Int) = getResult {
        apiService.getPeopleDetails(id, API_KEY);
    }

}