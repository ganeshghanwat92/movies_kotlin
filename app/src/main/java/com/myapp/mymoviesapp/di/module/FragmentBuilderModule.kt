package com.myapp.mymoviesapp.di.module

import com.myapp.mymoviesapp.ui.movies.MovieDetailFragment
import com.myapp.mymoviesapp.ui.movies.MovieHomeFragment
import com.myapp.mymoviesapp.ui.movies.SearchMoviesFragment
import com.myapp.mymoviesapp.ui.multisearch.MultiSearchFragment
import com.myapp.mymoviesapp.ui.person.PersonDetailsFragment
import com.myapp.mymoviesapp.ui.tvshow.TVShowDetailsFragment
import com.myapp.mymoviesapp.ui.tvshow.TvHomeFragment
import com.myapp.mymoviesapp.ui.tvshow.TvShowSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieHomeFragment() : MovieHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchMovieFragment() : SearchMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment() : MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeMultiSearchFragment() : MultiSearchFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment() : PersonDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeTvHomeFragment() : TvHomeFragment

    @ContributesAndroidInjector
    abstract fun contributeTvShowSearchFragment() : TvShowSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeTVShowDetailsFragment() : TVShowDetailsFragment


}