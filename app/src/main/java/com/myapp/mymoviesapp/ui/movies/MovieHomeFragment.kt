package com.myapp.mymoviesapp.ui.movies

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.myapp.mymoviesapp.MovieHomeViewModelFactory
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.datamodel.movie.Result
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource

class MovieHomeFragment : Fragment() {

    companion object {
        fun newInstance() = MovieHomeFragment()
    }

    private lateinit var viewModel: MovieHomeViewModel

    private lateinit var recyclerViewNowPlaying: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var recyclerViewTopRated: RecyclerView
    private lateinit var recyclerViewPopular: RecyclerView

    private lateinit var progressBarNowPlaying: ContentLoadingProgressBar
    private lateinit var progressBarUpcoming: ContentLoadingProgressBar
    private lateinit var progressBarTopRated: ContentLoadingProgressBar
    private lateinit var progressBarPopular: ContentLoadingProgressBar

    private lateinit var buttonSearchMovie: Button
    private lateinit var editText: EditText

    private val nowPlayingList = ArrayList<Result>()
    private val upcomingList = ArrayList<Result>()
    private val topRatedList = ArrayList<Result>()
    private val popularList = ArrayList<Result>()

    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.movie_home_fragment, container, false)

        setUpViews(view)

        setUpAdapters()

        return view
    }

    private fun setUpViews(view : View){

        recyclerViewNowPlaying = view.findViewById(R.id.recyclerViewNowPlayingMovies)
        recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcomingMovies)
        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRatedMovies)
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopularMovies)

        progressBarNowPlaying = view.findViewById(R.id.progressBarNowPlaying)
        progressBarUpcoming = view.findViewById(R.id.progressBarUpcoming)
        progressBarTopRated = view.findViewById(R.id.progressBarTopRated)
        progressBarPopular = view.findViewById(R.id.progressBarPopular)

        buttonSearchMovie = view.findViewById(R.id.buttonSearchMovie)
        editText = view.findViewById(R.id.editTextSearchMovie)

        buttonSearchMovie.setOnClickListener {
            val query : String = editText.text.toString()

            val bundle = bundleOf("query" to query)
            view.findNavController().navigate(R.id.searchMoviesFragment,bundle)

        }

    }

    private fun setUpAdapters(){

        nowPlayingAdapter = MovieAdapter(nowPlayingList,onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.movieDetailFragment,bundle)
        })
        upcomingAdapter = MovieAdapter(upcomingList, onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.movieDetailFragment,bundle)
        })
        topRatedAdapter = MovieAdapter(topRatedList, onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.movieDetailFragment,bundle)
        })
        popularAdapter = MovieAdapter(popularList, onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.movieDetailFragment,bundle)
        })

        recyclerViewNowPlaying.adapter = nowPlayingAdapter
        recyclerViewUpcoming.adapter = upcomingAdapter
        recyclerViewTopRated.adapter = topRatedAdapter
        recyclerViewPopular.adapter = popularAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, MovieHomeViewModelFactory(Repository(RemoteDataSource(ApiClient.apiService)))).get(MovieHomeViewModel::class.java)

        listenNowPlyingMoviesDataChange()
        listenUpcomingMoviesDataChange()
        listenTopRatedDataChange()
        listenPopularDataChange()

        viewModel.getNowPlayingMovies()
        viewModel.getUpcomingMovies()
        viewModel.getTopRatedMovies()
        viewModel.getPopularMovies()


    }

    private fun listenNowPlyingMoviesDataChange(){

        viewModel.nowPlayingMovieLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieSearchResponse>> {

                when(it) {

                    is ResultWrapper.Loading -> {
                        progressBarNowPlaying.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBarNowPlaying.visibility = View.INVISIBLE
                        nowPlayingList.addAll(it.value.results)
                        nowPlayingAdapter.notifyDataSetChanged()
                    }
                    is ResultWrapper.Error -> {
                        progressBarNowPlaying.visibility = View.INVISIBLE
                    }
                }
            })

    }

    private fun listenUpcomingMoviesDataChange(){

        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieSearchResponse>> {

                when(it) {

                    is ResultWrapper.Loading -> {  progressBarUpcoming.visibility = View.VISIBLE }
                    is ResultWrapper.Success -> {
                        progressBarUpcoming.visibility = View.INVISIBLE
                        upcomingList.addAll(it.value.results)
                        upcomingAdapter.notifyDataSetChanged()
                    }
                    is ResultWrapper.Error -> {
                        progressBarUpcoming.visibility = View.INVISIBLE
                    }
                }

            })

    }

    private fun listenTopRatedDataChange(){

        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieSearchResponse>> {

                when(it) {

                    is ResultWrapper.Loading -> { progressBarTopRated.visibility = View.VISIBLE}
                    is ResultWrapper.Success -> {
                        progressBarTopRated.visibility = View.INVISIBLE
                        topRatedList.addAll(it.value.results)
                        topRatedAdapter.notifyDataSetChanged()
                    }
                    is ResultWrapper.Error -> {
                        progressBarTopRated.visibility = View.INVISIBLE
                    }
                }

            })

    }

    private fun listenPopularDataChange(){

        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieSearchResponse>> {

                when(it) {

                    is ResultWrapper.Loading -> {progressBarPopular.visibility = View.VISIBLE}
                    is ResultWrapper.Success -> {
                        progressBarPopular.visibility = View.INVISIBLE
                        popularList.addAll(it.value.results)
                        popularAdapter.notifyDataSetChanged()
                    }
                    is ResultWrapper.Error -> {
                        progressBarPopular.visibility = View.INVISIBLE
                    }
                }
            })

    }

}