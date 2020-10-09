package com.example.moviesapp.ui.overviewfavoritesmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.MyApplication
import com.example.moviesapp.data.database.FavoriteMovie
import javax.inject.Inject

class OverviewFavoritesMoviesFragmentViewModel @Inject constructor() : ViewModel() {
    fun getFavoritesMovies() : LiveData<List<FavoriteMovie>> {
        return MyApplication.database.favoriteMovieDao.getFavoriteMovies()
    }
}