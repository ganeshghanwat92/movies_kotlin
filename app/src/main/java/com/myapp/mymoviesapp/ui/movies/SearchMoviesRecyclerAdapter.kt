package com.myapp.mymoviesapp.ui.movies

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.myapp.mymoviesapp.R
import com.myapp.mymoviesapp.Utils
import com.myapp.mymoviesapp.datamodel.movie.Result


class SearchMoviesRecyclerAdapter(private val list : ArrayList<Result?>, private val onItemClick : (id :Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1

    private var isLoadingAdded = false

 
    inner class ViewHolderItem(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

       val tvTitle = listItemView.findViewById<TextView>(R.id.textViewTitle)
       val tvOverview = listItemView.findViewById<TextView>(R.id.textViewOverview)
       val imageView  = listItemView.findViewById<ImageView>(R.id.imageViewPoster)
       val progressBar  = listItemView.findViewById<ProgressBar>(R.id.progressBar)


   }

    inner class ViewHolderLoading(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        val progressBar  = listItemView.findViewById<ProgressBar>(R.id.progressBar2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return if (viewType == VIEW_TYPE_ITEM) {
            // Inflate the custom layout
            val contactView = inflater.inflate(R.layout.recycler_view_item, parent, false)
            // Return a new holder instance
            ViewHolderItem(contactView)
        }else{
            val contactView = inflater.inflate(R.layout.recycler_view_item_loading, parent, false)
            // Return a new holder instance
            ViewHolderLoading(contactView)
        }
    }

    override fun getItemCount(): Int {
      return  list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(null)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = list.size - 1
        val result: Result? = getItem(position)
            list.removeAt(position)
            notifyItemRemoved(position)
    }

    fun add(movie: Result?) {
         list.add(movie)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<Result>) {
        for (result in moveResults) {
            add(result)
        }
    }

   fun getItem(position: Int): Result? {
        return list[position]
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movie = list[position]

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            val itemViewHolder : ViewHolderItem = holder as ViewHolderItem

            itemViewHolder.itemView.setOnClickListener {

                movie?.id?.let { it1 -> onItemClick(it1) }

            }

            itemViewHolder.tvOverview.text = movie?.overview
            itemViewHolder.tvTitle.text = movie?.title
            Glide
                .with(itemViewHolder.imageView.context)
                .load(Utils.buildBackdropPath(movie?.backdropPath))
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
                        itemViewHolder.progressBar.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemViewHolder.progressBar.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(itemViewHolder.imageView)

        }else{
            val loadingViewHolder : ViewHolderLoading = holder as ViewHolderLoading

            loadingViewHolder.progressBar.visibility = View.VISIBLE

        }

    }
}