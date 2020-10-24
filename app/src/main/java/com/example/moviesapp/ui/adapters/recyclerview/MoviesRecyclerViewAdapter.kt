package com.example.moviesapp.ui.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.databinding.ItemMovieRecyclerViewBinding

class MoviesRecyclerViewAdapter(private val items: List<Movie>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieRecyclerViewBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        holder.itemView.setOnClickListener{
            onClickListener.onClick(movie)
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit){
        fun onClick(movie: Movie) = clickListener(movie)
    }

    inner class ViewHolder(private val binding: ItemMovieRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.title = item.title
            binding.posterPath = item.posterPath
            binding.executePendingBindings()
        }
    }

}