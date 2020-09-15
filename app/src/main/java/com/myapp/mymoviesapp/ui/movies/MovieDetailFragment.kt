package com.myapp.mymoviesapp.ui.movies

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.myapp.mymoviesapp.MovieDetailsViewModelFactory
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.datamodel.movie.MovieDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,MovieDetailsViewModelFactory(Repository(RemoteDataSource(ApiClient.apiService)))).get(MovieDetailViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id : Int? = arguments?.getInt("id")

        Log.d("SSSSS","Movie details id = "+id)

        viewModel.liveDataMovie.observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieDetails>> { t ->
                when(t){
                    is ResultWrapper.Loading ->{

                        progressBarMovieDetails.visibility = View.VISIBLE

                    }
                    is ResultWrapper.Success -> {
                        progressBarMovieDetails.visibility = View.INVISIBLE

                        updateUi(t.value)

                    }
                    is ResultWrapper.Error -> {

                        progressBarMovieDetails.visibility = View.INVISIBLE

                    }
                }
            })

        id?.let { viewModel.getMovieDetails(it) }

    }

    private fun updateUi(movieDetails: MovieDetails){

        textViewMovieName.text = movieDetails.title
        textViewMovieOverview.text = movieDetails.overview
        textViewReleaseDate.text = movieDetails.releaseDate
        textViewMovieRuntime.text = movieDetails.runtime.toString()
        textViewMovieRatings.text = movieDetails.voteAverage.toString()

        Glide
            .with(this)
            .load(Utils.buildBackdropPath(movieDetails.backdropPath))
            // .centerCrop()
            //  .placeholder(circularProgress)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                 //   holder.progressBar.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                  //  holder.progressBar.visibility = View.INVISIBLE
                    return false
                }
            })
            .into(imageViewMoviePoster);


    }
}