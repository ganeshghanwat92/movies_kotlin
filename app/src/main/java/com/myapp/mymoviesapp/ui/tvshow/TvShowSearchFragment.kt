package com.myapp.mymoviesapp.ui.tvshow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.ViewModelFactory
import com.myapp.mymoviesapp.datamodel.tv.Result
import com.myapp.mymoviesapp.repository.ResultWrapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.tv_show_search_fragment.*
import javax.inject.Inject

class TvShowSearchFragment : Fragment() {

    companion object {
        fun newInstance() = TvShowSearchFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: TvShowSearchViewModel by viewModels { viewModelFactory }

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var adapter: SearchTVShowsRecyclerAdapter
    private var list = ArrayList<Result>()

    var query : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_show_search_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         query = arguments?.getString("query")

        recyclerView = recyclerViewTVShows
        progressBar =  progressBarSearchTVShow
        progressBar.visibility = View.INVISIBLE
        adapter = SearchTVShowsRecyclerAdapter(list, onItemClick = {
            val bundle = bundleOf("id" to it)
            view.findNavController().navigate(R.id.TVShowDetailsFragment,bundle)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity);

        viewModel.tvShowsLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {

                is ResultWrapper.Success -> {

                    Log.d("ViewModel", "onChange serachMovies" + it.value.toString())

                    list.clear()
                    list.addAll(it.value.results)

                    adapter.notifyDataSetChanged()

                    progressBar.visibility = View.INVISIBLE
                    recyclerViewTVShows.visibility = View.VISIBLE
                    errorLayout.visibility = View.INVISIBLE


                }
                is ResultWrapper.Error -> {

                    Log.d("ViewModel", "onChange serachMovies Exception " + it.message)

                    it.ex.printStackTrace()

                    progressBar.visibility = View.INVISIBLE
                    recyclerViewTVShows.visibility = View.INVISIBLE
                    errorLayout.visibility = View.VISIBLE
                    textViewError.text = it.message

                }

                is ResultWrapper.Loading -> {

                    Log.d("ViewModel", "onChange serachMovies Loading " + it.boolean)

                    if (it.boolean)
                        progressBar.visibility = View.VISIBLE
                    else
                        progressBar.visibility = View.INVISIBLE

                }

            }
        })


        buttonSearchShow.setOnClickListener {

            progressBar.visibility = View.VISIBLE

             query = editTextSearchShows.text.toString()

            viewModel.searchTVShows(query!!)

        }

        query?.let {  if (it.isNotEmpty()) viewModel.searchTVShows(it) }

        buttonRetry.setOnClickListener {

            query?.let {  if (it.isNotEmpty()) viewModel.searchTVShows(it) }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}