package com.myapp.mymoviesapp.ui.multisearch

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.mymoviesapp.MultiSearchViewModelFactory
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.datamodel.multisearch.Result
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import com.myapp.mymoviesapp.ui.movies.SearchMoviesRecyclerAdapter
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.multi_search_fragment.*
import kotlinx.android.synthetic.main.search_movies_fragment.*

class MultiSearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            MultiSearchFragment()
    }

    private lateinit var viewModel: MultiSearchViewModel

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MultiSearchAdapter
    var list = ArrayList<Result>()

    var q : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.multi_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = recyclerViewMultiSearch
        progressBar = progressBarSearchAll
        progressBar.visibility = View.INVISIBLE

        buttonSearchAll.setOnClickListener {

            progressBar.visibility = View.VISIBLE

             q = editTextSearchAll.text.toString()

            activity?.let { it1 -> Utils.hideSoftKeyboard(it1) }

           q?.let { viewModel.multiSearch(it) }

        }

        adapter = MultiSearchAdapter(list, onItemClick = {

            val bundle = bundleOf("id" to it.id)
            when(it.mediaType) {
                "movie" -> {
                    view.findNavController().navigate(R.id.movieDetailFragment,bundle)
            }
                "tv" -> {
                    view.findNavController().navigate(R.id.TVShowDetailsFragment,bundle)
                }

                "person" -> {
                    view.findNavController().navigate(R.id.personDetailsFragment,bundle)
                }

            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity);

        listenLiveDataChange()

        buttonRetry.setOnClickListener {
            q?.let { viewModel.multiSearch(it) }
        }



    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,MultiSearchViewModelFactory(Repository(RemoteDataSource(ApiClient.apiService)))).get(MultiSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun listenLiveDataChange() {

        viewModel.multiSearchLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {

                is ResultWrapper.Success -> {

                    Log.d("ViewModel", "onChange serachMovies" + it.value.toString())

                    list.clear()
                    list.addAll(it.value.results)

                    adapter.notifyDataSetChanged()

                    progressBar.visibility = View.INVISIBLE
                    errorLayout.visibility = View.INVISIBLE
                    recyclerViewMultiSearch.visibility = View.VISIBLE


                }
                is ResultWrapper.Error -> {

                    Log.d("ViewModel", "onChange serachMovies Exception " + it.message)

                    it.ex.printStackTrace()

                    progressBar.visibility = View.INVISIBLE
                    textViewError.text = it.message
                    errorLayout.visibility = View.VISIBLE
                    recyclerViewMultiSearch.visibility = View.INVISIBLE

                }

                is ResultWrapper.Loading -> {

                    Log.d("ViewModel", "onChange serachMovies Loading " + it.boolean)

                        progressBar.visibility = View.VISIBLE
                    errorLayout.visibility = View.INVISIBLE

                }

            }
        })

    }


}