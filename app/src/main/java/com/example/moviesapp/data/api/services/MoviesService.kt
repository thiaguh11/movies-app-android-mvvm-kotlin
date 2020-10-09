package com.example.moviesapp.data.api.services

import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String
    ) : Response<Result>?

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ) : Response<Movie>?

    @GET("search/movie")
    suspend fun getSearchResult(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : Response<Result>?

}