package com.example.moviesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.data.model.Movie

@Entity
data class FavoriteMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String
)

fun Movie.toFavoriteMovie() : FavoriteMovie {
    return FavoriteMovie(id, title, posterPath.toString())
}
