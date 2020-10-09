package com.example.moviesapp.ui.di

import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.ui.moviedetails.MovieDetailsFragment
import com.example.moviesapp.ui.overviewfavoritesmovies.OverviewFavoritesMoviesFragment
import com.example.moviesapp.ui.overviewmovies.OverviewMoviesFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create() : MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(overviewMoviesFragment: OverviewMoviesFragment)
    fun inject(overviewFavoritesMoviesFragment: OverviewFavoritesMoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

}