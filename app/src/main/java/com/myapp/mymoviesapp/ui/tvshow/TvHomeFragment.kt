package com.myapp.mymoviesapp.ui.tvshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.ViewModelFactory
import com.myapp.mymoviesapp.datamodel.tv.Result
import com.myapp.mymoviesapp.datamodel.tv.TVSearchResponse
import com.myapp.mymoviesapp.repository.ResultWrapper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class TvHomeFragment : Fragment() {

    @Inject
     lateinit var viewModelFactory: ViewModelFactory

   private val viewModel: TvHomeViewModel by viewModels { viewModelFactory }

    private lateinit var recyclerViewOnAir: RecyclerView
    private lateinit var recyclerViewPopular: RecyclerView
    private lateinit var recyclerViewTopRated: RecyclerView

    private lateinit var progressBarOnAir: ContentLoadingProgressBar
    private lateinit var progressBarPopular: ContentLoadingProgressBar
    private lateinit var progressBarTopRated: ContentLoadingProgressBar

    private lateinit var buttonSearchTv: Button
    private lateinit var editText: EditText

    private val onAirList = ArrayList<Result>()
    private val topRatedList = ArrayList<Result>()
    private val popularList = ArrayList<Result>()

    private lateinit var onAirAdapter: TVShowAdapter
    private lateinit var topRatedAdapter: TVShowAdapter
    private lateinit var popularAdapter: TVShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_home, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews(view)

        setUpAdapters()

        listenOnAirDataChange()
        listenPopularDataChange()
        listenTopRatedDataChange()

        viewModel.getOnAirTVShows()
        viewModel.getPopular()
        viewModel.getTopRated()

    }

    private fun setUpViews(view: View){

        recyclerViewOnAir = view.findViewById(R.id.recyclerViewOnAir)
        recyclerViewTopRated = view.findViewById(R.id.recyclerViewTopRatedTV)
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopularTV)

        progressBarOnAir = view.findViewById(R.id.progressBarOnAir)
        progressBarTopRated = view.findViewById(R.id.progressBarTopRated)
        progressBarPopular = view.findViewById(R.id.progressBarPopular)

        buttonSearchTv = view.findViewById(R.id.buttonSearchTV)
        editText = view.findViewById(R.id.editTextSearchTV)

        buttonSearchTv.setOnClickListener {
            val query : String = editText.text.toString()

            val bundle = bundleOf("query" to query)
            view.findNavController().navigate(R.id.tvShowSearchFragment,bundle)

        }

    }

    private fun setUpAdapters(){

        onAirAdapter = TVShowAdapter(onAirList,onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.TVShowDetailsFragment,bundle)
        })
        topRatedAdapter = TVShowAdapter(topRatedList,onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.TVShowDetailsFragment,bundle)
        })
        popularAdapter = TVShowAdapter(popularList,onItemClick = {
            val bundle = bundleOf("id" to it)
            view?.findNavController()?.navigate(R.id.TVShowDetailsFragment,bundle)
        })

        recyclerViewOnAir.adapter = onAirAdapter
        recyclerViewTopRated.adapter = topRatedAdapter
        recyclerViewPopular.adapter = popularAdapter

    }

    private fun listenOnAirDataChange(){

        viewModel.onAirLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<TVSearchResponse>> {

                when(it) {

                    is ResultWrapper.Loading -> {
                        progressBarOnAir.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBarOnAir.visibility = View.INVISIBLE
                        onAirList.addAll(it.value.results)
                        onAirAdapter.notifyDataSetChanged()
                    }
                    is ResultWrapper.Error -> {
                        progressBarOnAir.visibility = View.INVISIBLE
                    }
                }
            })

    }

    private fun listenTopRatedDataChange(){

        viewModel.topRatedLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<TVSearchResponse>> {

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

        viewModel.popularLiveData.observe(viewLifecycleOwner,
            Observer<ResultWrapper<TVSearchResponse>> {

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