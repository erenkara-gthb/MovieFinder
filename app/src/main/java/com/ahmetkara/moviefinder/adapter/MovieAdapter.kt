package com.ahmetkara.moviefinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkara.moviefinder.R
import com.ahmetkara.moviefinder.databinding.ItemMovieBinding
import com.ahmetkara.moviefinder.model.MovieResult


class MovieAdapter(val movieDetailList: List<MovieResult>, val onItemClick : (MovieResult) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    class MovieViewHolder(var view : ItemMovieBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(model: MovieResult, onItemClick: (MovieResult) -> Unit){
            itemView.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie
                , parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieDetailList[position]
        holder.bind(movieDetailList[position],onItemClick)
    }

    override fun getItemCount(): Int {
        return movieDetailList.size
    }
}