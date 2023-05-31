package com.ahmetkara.moviefinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkara.moviefinder.R
import com.ahmetkara.moviefinder.databinding.ItemGenreBinding
import com.ahmetkara.moviefinder.model.detail.MovieGenre

class GenreAdapter(var genreList : List<MovieGenre>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    class GenreViewHolder(val binding : ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemGenreBinding>(inflater, R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.binding.genre = genreList[position]
    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}