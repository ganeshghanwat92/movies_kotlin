package com.myapp.mymoviesapp.ui.movies

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.SearchMoviesViewModelFactory
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.datamodel.movie.MovieSearchResponse
import com.myapp.mymoviesapp.repository.Repository
import com.myapp.mymoviesapp.repository.ResultWrapper
import com.myapp.mymoviesapp.repository.remote.ApiClient
import com.myapp.mymoviesapp.repository.remote.RemoteDataSource
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.search_movies_fragment.*


class SearchMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = SearchMoviesFragment()
    }

    private lateinit var viewModel: SearchMoviesViewModel

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SearchMoviesRecyclerAdapter
    var list = ArrayList<com.myapp.mymoviesapp.datamodel.movie.Result?>()

    var query : String? = null

    private var isLoading = false
    private var isLastPage = false
    private var totalPages = 0
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            SearchMoviesViewModelFactory(Repository(RemoteDataSource(ApiClient.apiService)))
        ).get(SearchMoviesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         query  = arguments?.getString("query")

        activity?.title = "Search"

        recyclerView = recyclerViewMovies
        progressBar = progressBarSearchMovies
        progressBar.visibility = View.INVISIBLE

        buttonSearchMovie.setOnClickListener {

            progressBar.visibility = View.VISIBLE

             query = editTextSearchMovie.text.toString()

            activity?.let { it1 -> Utils.hideSoftKeyboard(it1) }

            query?.let {  viewModel.searchMovies(it) }

        }

        adapter = SearchMoviesRecyclerAdapter(list, onItemClick = {

            Log.d("SSSSS","id = "+it)
            val bundle = bundleOf("id" to it)
            view.findNavController().navigate(R.id.movieDetailFragment,bundle)

        })

        val linearLayoutManager = LinearLayoutManager(activity)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun isLoading(): Boolean {
                    return isLoading
            }

            override fun isLastPage(): Boolean {
                    return isLastPage
            }

            override fun loadMoreItems() {
                isLoading = true;
                loadNextPage()

            }

        })



        listenLiveDataChange()

        query?.let {  if (it.isNotEmpty()) viewModel.searchMovies(it) }

        buttonRetry.setOnClickListener {

            query?.let {  if (it.isNotEmpty()) viewModel.searchMovies(it) }

        }


    }

    private fun loadNextPage(){

        query?.let { viewModel.loadMoviesNextPage(it, currentPage +1).observe(viewLifecycleOwner,
            Observer<ResultWrapper<MovieSearchResponse>> {

                when (it) {

                    is ResultWrapper.Success -> {

                        Log.d("ViewModel", "onChange serachMovies" + it.value.toString())

                        Handler().postDelayed({

                        adapter.removeLoadingFooter()
                        isLoading = false

                        currentPage = it.value.page
                        totalPages = it.value.totalPages


                        adapter.addAll(it.value.results)

                        if (currentPage <= totalPages)
                            adapter.addLoadingFooter()
                        else
                            isLastPage = true

                        },5000)

                    }
                    is ResultWrapper.Error -> {

                        Log.d("ViewModel", "onChange serachMovies Exception " + it.message)

                        it.ex.printStackTrace()



                    }

                    is ResultWrapper.Loading -> {

                        Log.d("ViewModel", "onChange serachMovies Loading " + it.boolean)

                    }

                }

            })
        }
    }

     private fun listenLiveDataChange() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer {

            when (it) {

                is ResultWrapper.Success -> {

                    Log.d("ViewModel", "onChange serachMovies" + it.value.toString())

                    currentPage = it.value.page
                    totalPages = it.value.totalPages


                    list.clear()
                    list.addAll(it.value.results)

                    adapter.notifyDataSetChanged()

                    progressBar.visibility = View.INVISIBLE
                    errorLayout.visibility = View.INVISIBLE
                    recyclerViewMovies.visibility = View.VISIBLE

                    if (currentPage <= totalPages)
                        adapter.addLoadingFooter()
                    else
                        isLastPage = true



                }
                is ResultWrapper.Error -> {

                    Log.d("ViewModel", "onChange serachMovies Exception " + it.message)

                    it.ex.printStackTrace()

                    progressBar.visibility = View.INVISIBLE
                    textViewError.text = it.message
                    errorLayout.visibility = View.VISIBLE
                    recyclerViewMovies.visibility = View.INVISIBLE


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