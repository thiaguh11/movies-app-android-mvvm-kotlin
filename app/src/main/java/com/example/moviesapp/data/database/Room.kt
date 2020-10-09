package com.example.moviesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.data.database.dao.FavoriteMovieDao

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMoviesDatabase: RoomDatabase() {
    abstract val favoriteMovieDao:FavoriteMovieDao
}

private lateinit var INSTANCE: FavoriteMoviesDatabase

fun getDatabase(context: Context): FavoriteMoviesDatabase {
    synchronized(FavoriteMoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                FavoriteMoviesDatabase::class.java,
                "movies").build()
        }
    }
    return INSTANCE
}