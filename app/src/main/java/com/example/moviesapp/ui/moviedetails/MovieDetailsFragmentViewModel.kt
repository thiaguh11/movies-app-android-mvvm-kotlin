package com.example.moviesapp.ui.moviedetails

import androidx.lifecycle.*
import com.example.moviesapp.MyApplication
import com.example.moviesapp.data.database.FavoriteMovie
import com.example.moviesapp.data.model.Resource
import com.example.moviesapp.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragmentViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {

    val isFavorite = MutableLiveData(false)

    init {
        isFavorite.value = false
    }

    fun getMovieDetailsById(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = moviesRepository.getMovieDetailsById(movieId)
            if(response != null && response.isSuccessful)
                emit(Resource.success(data = response.body()))
            else
                emit(Resource.error(data = null, message = "Error Occurred!"))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch {
        isFavorite.value = true
        MyApplication.database.favoriteMovieDao.insertFavoriteMovie(favoriteMovie)
    }

    fun getFavoriteMovieById(movieId: Int) : LiveData<FavoriteMovie> {
        return MyApplication.database.favoriteMovieDao.getFavoriteMovieById(movieId)
    }

    fun deleteFavoriteMovie(movieId: Int) = viewModelScope.launch {
        isFavorite.value = false
        MyApplication.database.favoriteMovieDao.deleteFavoriteMovie(movieId)
    }

}