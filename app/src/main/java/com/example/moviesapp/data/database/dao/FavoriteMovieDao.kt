package com.example.moviesapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesapp.data.database.FavoriteMovie

@Dao
interface FavoriteMovieDao {
    @Query("select * from favoritemovie")
    fun getFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("select * from favoritemovie where id=:id")
    fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("delete from favoritemovie where id=:id")
    suspend fun deleteFavoriteMovie(id: Int)
}