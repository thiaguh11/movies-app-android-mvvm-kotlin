package com.example.moviesapp.data.repository

import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.Result
import retrofit2.Response

interface MoviesRepository {

    suspend fun getMoviesNowPlaying(page: String = "1") : Response<Result>?

    suspend fun getMovieDetailsById(movieId: Int) : Response<Movie>?

    suspend fun getSearchResult(query: String) : Response<Result>?

}