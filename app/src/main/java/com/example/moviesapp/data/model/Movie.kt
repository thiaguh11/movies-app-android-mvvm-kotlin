package com.example.moviesapp.data.model

import com.squareup.moshi.Json

data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "vote_average")
    val voteAverage: String = "",
    @Json(name = "backdrop_path")
    val backdropPath: String? = "",
    val overview: String = "",
    @Json(name = "release_date")
    val releaseDate: String = "",
    val genres: List<Genre> = listOf()
)