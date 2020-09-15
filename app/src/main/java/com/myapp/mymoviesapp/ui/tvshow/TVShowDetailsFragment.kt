package com.myapp.mymoviesapp.ui.tvshow

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.TVShowDetailsViewModelFactory
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.datamodel.tv.TVShowDetails
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.movie_detail_fragment.textViewReleaseDate
import kotlinx.android.synthetic.main.tv_show_details_fragment.*

class TVShowDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TVShowDetailsFragment()
    }

    private lateinit var viewModel: TVShowDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, TVShowDetailsViewModelFactory(Repository(
            RemoteDataSource(ApiClient.apiService)
        ))).get(TVShowDetailsViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_show_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id : Int? = arguments?.getInt("id")

        id?.let { viewModel.getTVShowsDetails(it) }

        buttonRetry.setOnClickListener {

            id?.let { viewModel.getTVShowsDetails(it) }


        }

        viewModel.tvShowsLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<TVShowDetails>> { t ->
                when(t){
                    is ResultWrapper.Loading ->{

                        progressBarTvShowDetails.visibility = View.VISIBLE

                    }
                    is ResultWrapper.Success -> {
                        progressBarTvShowDetails.visibility = View.INVISIBLE
                        tvshowDetailsLayout.visibility = View.VISIBLE
                        errorLayout.visibility = View.INVISIBLE

                        updateUi(t.value)

                    }
                    is ResultWrapper.Error -> {

                        progressBarTvShowDetails.visibility = View.INVISIBLE
                        tvshowDetailsLayout.visibility = View.INVISIBLE
                        errorLayout.visibility = View.VISIBLE
                        textViewError.text = t.message


                    }
                }
            })



    }

    private fun updateUi(details: TVShowDetails){

        textViewTvShowName.text = details.name
        textViewTvShowOverview.text = details.overview
        textViewReleaseDate.text = details.firstAirDate
        textViewRuntime.text = details.episodeRunTime.toString()
        textViewRatings.text = details.voteAverage.toString()

        Glide
            .with(this)
            .load(Utils.buildBackdropPath(details.backdropPath))
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
            .into(imageViewTvShowPoster);


    }


}