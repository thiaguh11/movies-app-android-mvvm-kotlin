package com.example.moviesapp.di

import com.example.moviesapp.data.repository.MoviesRepository
import com.example.moviesapp.data.repository.RemoteMoviesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun provideRemoteMoviesRepository(moviesRepository: RemoteMoviesRepository) : MoviesRepository
}