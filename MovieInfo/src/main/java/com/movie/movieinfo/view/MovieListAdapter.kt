package com.movie.movieinfo.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.info.model.Search
import com.movie.movieinfo.R
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(var context: Context,var movieList: ArrayList<Search>) : RecyclerView.Adapter<MovieListAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.movie_list_item, parent, false))

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieListAdapter.PostViewHolder, position: Int) = holder.bind(movieList[position])

    fun refreshAdapter(newPostList: List<Search>) {
        Log.d("MovieListAdapter", "Search Items:::$newPostList")
        movieList.clear()
        movieList.addAll(newPostList)
        notifyDataSetChanged()
        }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val layout = view.movie_item_parent
        private val year = view.year_text
        private val title = view.title_text
        private val imagePoster = view.avatar_image
        fun bind(model: Search) {
            Log.d("PostAdapter", "In bind() method.......")
            title.text = model.Title
            year.text = model.Year
            Glide
                .with(context)
                .load(model.Poster)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .into(imagePoster);
        }
    }
}