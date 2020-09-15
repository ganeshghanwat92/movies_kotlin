package com.myapp.mymoviesapp.ui.movies

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = linearLayoutManager.childCount
        val totalItemCount: Int = linearLayoutManager.itemCount
        val firstVisibleItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    abstract fun isLoading() : Boolean

    abstract fun isLastPage() : Boolean

    abstract fun loadMoreItems()

}