package com.myapp.mymoviesapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.mymoviesapp.MainActivityViewModel
import com.myapp.mymoviesapp.ViewModelFactory
import com.myapp.mymoviesapp.di.ViewModelKey
import com.myapp.mymoviesapp.ui.movies.MovieDetailViewModel
import com.myapp.mymoviesapp.ui.movies.MovieHomeViewModel
import com.myapp.mymoviesapp.ui.movies.SearchMoviesViewModel
import com.myapp.mymoviesapp.ui.multisearch.MultiSearchViewModel
import com.myapp.mymoviesapp.ui.person.PersonDetailsViewModel
import com.myapp.mymoviesapp.ui.tvshow.TVShowDetailsViewModel
import com.myapp.mymoviesapp.ui.tvshow.TvHomeViewModel
import com.myapp.mymoviesapp.ui.tvshow.TvShowSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(MovieHomeViewModel::class)
   abstract fun bindMovieHomeViewModel(movieHomeViewModel: MovieHomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchMoviesViewModel::class)
    abstract fun bindSearchMovieViewModel(searchMoviesViewModel: SearchMoviesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailsViewModel(movieDetailViewModel: MovieDetailViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MultiSearchViewModel::class)
    abstract fun bindMultiSearchViewModel(multiSearchViewModel: MultiSearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonDetailsViewModel::class)
    abstract fun buildPersonDetailsViewModel(personDetailsViewModel: PersonDetailsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvHomeViewModel::class)
    abstract fun buildTvHomeViewModel(tvHomeViewModel: TvHomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowSearchViewModel::class)
    abstract fun buildTvShowSearchViewModel(tvShowSearchViewModel: TvShowSearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TVShowDetailsViewModel::class)
    abstract fun buildTVShowDetailsViewModel(tvShowDetailsViewModel: TVShowDetailsViewModel) : ViewModel
}