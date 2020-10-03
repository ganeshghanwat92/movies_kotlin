package com.myapp.mymoviesapp.ui.person

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.ViewModelFactory
import com.myapp.mymoviesapp.datamodel.people.Person
import com.myapp.mymoviesapp.repository.ResultWrapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_person_details.*
import javax.inject.Inject

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PersonDetailsFragment : Fragment() {
    private val hideHandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PersonDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id : Int? = arguments?.getInt("id")

        id?.let { viewModel.getPersonDetails(it) }

        buttonRetry.setOnClickListener {
            id?.let { viewModel.getPersonDetails(it) }
        }

        viewModel.personDetailsLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<Person>> { t ->
                when(t){
                    is ResultWrapper.Loading ->{

                        progressBarPerson.visibility = View.VISIBLE
                        errorLayout.visibility = View.INVISIBLE

                    }
                    is ResultWrapper.Success -> {
                        progressBarPerson.visibility = View.INVISIBLE
                        errorLayout.visibility = View.INVISIBLE

                        textViewName.text = t.value.name
                        textViewBiography.text = t.value.biography

                        Glide
                            .with(this)
                            .load(Utils.buildBackdropPath(t.value.profilePath))
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
                            .into(imageViewPerson);

                    }
                    is ResultWrapper.Error -> {

                        progressBarPerson.visibility = View.INVISIBLE
                        errorLayout.visibility = View.VISIBLE
                    }
                }
            })
    }
}