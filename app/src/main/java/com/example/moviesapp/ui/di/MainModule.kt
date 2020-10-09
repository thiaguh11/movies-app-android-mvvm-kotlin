package com.example.moviesapp.ui.di

import androidx.lifecycle.ViewModel
import com.example.moviesapp.di.ViewModelKey
import com.example.moviesapp.ui.moviedetails.MovieDetailsFragmentViewModel
import com.example.moviesapp.ui.overviewfavoritesmovies.OverviewFavoritesMoviesFragmentViewModel
import com.example.moviesapp.ui.overviewmovies.OverviewMoviesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(OverviewMoviesFragmentViewModel::class)
    fun bindOverviewMoviesViewModel(viewModel: OverviewMoviesFragmentViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OverviewFavoritesMoviesFragmentViewModel::class)
    fun bindOverviewFavoritesMoviesFragmentViewModel(viewModel: OverviewFavoritesMoviesFragmentViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsFragmentViewModel::class)
    fun bindMovieDetailsFragmentViewModel(viewModel: MovieDetailsFragmentViewModel) : ViewModel

}