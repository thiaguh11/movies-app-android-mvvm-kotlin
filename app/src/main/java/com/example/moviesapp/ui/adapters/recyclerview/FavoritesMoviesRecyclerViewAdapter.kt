package com.example.moviesapp.ui.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.data.database.FavoriteMovie
import com.example.moviesapp.databinding.ItemMovieRecyclerViewBinding

class FavoritesMoviesRecyclerViewAdapter(private val items: List<FavoriteMovie>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<FavoritesMoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieRecyclerViewBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteMovie = items[position]
        holder.itemView.setOnClickListener{
            onClickListener.onClick(favoriteMovie)
        }
        holder.bind(favoriteMovie)
    }

    class OnClickListener(val clickListener: (favoriteMovie: FavoriteMovie) -> Unit){
        fun onClick(favoriteMovie: FavoriteMovie) = clickListener(favoriteMovie)
    }

    inner class ViewHolder(private val binding: ItemMovieRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteMovie: FavoriteMovie) {
            binding.title = favoriteMovie.title
            binding.posterPath = favoriteMovie.posterPath
            binding.executePendingBindings()
        }
    }

}