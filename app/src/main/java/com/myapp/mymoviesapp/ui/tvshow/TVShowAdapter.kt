package com.myapp.mymoviesapp.ui.tvshow

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
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

class TVShowAdapter(private val myDataset: ArrayList<Result>, private val onItemClick : (Int) -> Unit) :
        RecyclerView.Adapter<TVShowAdapter.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val imageView : ImageView = view.findViewById(R.id.imageViewMoviePoster)
            val progressBar : ProgressBar = view.findViewById(R.id.progressBar)
        }


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): TVShowAdapter.MyViewHolder {
            // create a new view
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item_layout, parent, false) as View
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.itemView.setOnClickListener {
                onItemClick(myDataset[position].id)
            }

            Glide
                .with(holder.imageView.context)
                .load(Utils.buildBackdropPath(myDataset[position].posterPath))
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

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }