package com.myapp.mymoviesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.mymoviesapp.ui.movies.MovieDetailViewModel
import com.myapp.mymoviesapp.ui.movies.SearchMoviesViewModel
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.ui.movies.MovieHomeViewModel
import com.myapp.mymoviesapp.ui.multisearch.MultiSearchViewModel
import com.myapp.mymoviesapp.ui.person.PersonDetailsViewModel
import com.myapp.mymoviesapp.ui.tvshow.TVShowDetailsViewModel
import com.myapp.mymoviesapp.ui.tvshow.TvHomeViewModel
import com.myapp.mymoviesapp.ui.tvshow.TvShowSearchViewModel

class MainActivityViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MainActivityViewModel(repo) as T
    }

}
class SearchMoviesViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SearchMoviesViewModel(repo) as T
    }

}

class SearchTVShowsViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return TvShowSearchViewModel(repo) as T
    }
}


class MovieDetailsViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MovieDetailViewModel(repo) as T
    }
}

    class TVShowDetailsViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return TVShowDetailsViewModel(repo) as T
        }
}

class MultiSearchViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MultiSearchViewModel(repo) as T
    }
}

class PersonDetailsViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return PersonDetailsViewModel(repo) as T
    }
}

class MovieHomeViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MovieHomeViewModel(repo) as T
    }
}

class TvHomeViewModelFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return TvHomeViewModel(repo) as T
    }
}
