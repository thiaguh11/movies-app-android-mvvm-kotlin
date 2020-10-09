package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.data.database.FavoriteMoviesDatabase
import com.example.moviesapp.data.database.getDatabase
import com.example.moviesapp.di.ApplicationComponent
import com.example.moviesapp.di.DaggerApplicationComponent

class MyApplication : Application() {

    companion object {
        lateinit var database: FavoriteMoviesDatabase
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        database = getDatabase(applicationContext)
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}