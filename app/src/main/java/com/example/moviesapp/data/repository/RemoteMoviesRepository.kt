package com.example.moviesapp.data.repository

import com.example.moviesapp.data.api.services.MoviesService
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.Result
import com.example.moviesapp.utils.API_KEY
import retrofit2.Response
import javax.inject.Inject

class RemoteMoviesRepository @Inject constructor(private val moviesService: MoviesService) : MoviesRepository {

    override suspend fun getMoviesNowPlaying(page: String) : Response<Result>? = moviesService.getMoviesNowPlaying(
        apiKey = API_KEY,
        language = "pt-BR",
        page = page
    )

    override suspend fun getMovieDetailsById(movieId: Int) : Response<Movie>? = moviesService.getMovieDetailsById(
        movieId = movieId,
        apiKey = API_KEY,
        language = "pt-BR"
    )

    override suspend fun getSearchResult(query: String) : Response<Result>? = moviesService.getSearchResult(
        apiKey = API_KEY,
        query = query
    )

}