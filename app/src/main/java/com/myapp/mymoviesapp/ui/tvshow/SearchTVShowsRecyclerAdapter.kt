package com.myapp.mymoviesapp.ui.tvshow

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
import com.myapp.mymoviesapp.datamodel.tv.Result

class SearchTVShowsRecyclerAdapter(private val list : List<Result>, private val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<SearchTVShowsRecyclerAdapter.ViewHolder>() {


   inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

       val tvTitle = listItemView.findViewById<TextView>(R.id.textViewTitle)
       val tvOverview = listItemView.findViewById<TextView>(R.id.textViewOverview)
       val imageView = listItemView.findViewById<ImageView>(R.id.imageViewPoster)
       val progressBar  = listItemView.findViewById<ProgressBar>(R.id.progressBar)



   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.recycler_view_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
      return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = list[position]

        holder.itemView.setOnClickListener {
            onItemClick(movie.id)
        }

        holder.tvOverview.text = movie.overview
        holder.tvTitle.text = movie.originalName

        Glide
            .with(holder.imageView.context)
            .load(Utils.buildBackdropPath(movie.backdropPath))
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
                    holder.progressBar.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.INVISIBLE
                    return false
                }
            })
            .into(holder.imageView);

    }
}