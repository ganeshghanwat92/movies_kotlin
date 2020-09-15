package com.myapp.mymoviesapp.ui.multisearch

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
import com.myapp.mymoviesapp.datamodel.multisearch.Result

class MultiSearchAdapter(private val myDataset: ArrayList<Result>, private val onItemClick : (result : Result) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_MOVIE = 1
    val TYPE_PERSON = 2

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val tvTitle = view.findViewById<TextView>(R.id.textViewTitle)
            val tvOverview = view.findViewById<TextView>(R.id.textViewOverview)
            val imageView  = view.findViewById<ImageView>(R.id.imageViewPoster)
            val progressBar  = view.findViewById<ProgressBar>(R.id.progressBar)
        }


    class PersonViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val imageView : ImageView = view.findViewById(R.id.imageViewPerson)
        val textView : TextView = view.findViewById(R.id.textViewPersonName)
    }


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): RecyclerView.ViewHolder {
            // create a new view
            if (viewType == TYPE_MOVIE) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_item, parent, false) as View
                // set the view's size, margins, paddings and layout parameters
                return MyViewHolder(view)
            }else{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_item_person, parent, false) as View
                // set the view's size, margins, paddings and layout parameters
                return PersonViewHolder(view)
            }
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.itemView.setOnClickListener {
                onItemClick(myDataset[position])
            }

            if (getItemViewType(position) == TYPE_MOVIE){

                val movieHolder = holder as MyViewHolder

                movieHolder.tvTitle.text = myDataset[position].originalTitle
                movieHolder.tvOverview.text = myDataset[position].overview
                Glide
                    .with(movieHolder.imageView.context)
                    .load(Utils.buildBackdropPath(myDataset[position].backdropPath))
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
                            movieHolder.progressBar.visibility = View.INVISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            movieHolder.progressBar.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(movieHolder.imageView);

            }else{

                val personHolder = holder as PersonViewHolder

                personHolder.textView.text = myDataset[position].name
                Glide
                    .with(personHolder.imageView.context)
                    .load(Utils.buildBackdropPath(myDataset[position].profilePath))
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
                          //  movieHolder.progressBar.visibility = View.INVISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                          //  movieHolder.progressBar.visibility = View.INVISIBLE
                            return false
                        }
                    })
                    .into(personHolder.imageView);

            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size

    override fun getItemViewType(position: Int): Int {
        return if (myDataset[position].mediaType == "person"){
            TYPE_PERSON
        }else{
            TYPE_MOVIE
        }
    }
    }