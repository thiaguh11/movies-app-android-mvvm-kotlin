package com.example.moviesapp.ui.overviewmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moviesapp.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class OverviewMoviesFragmentViewModel @Inject constructor (private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> get() = _hasError

    fun getMoviesNowPlaying(page: String = "1", loadMore: Boolean = false) = liveData(Dispatchers.IO) {
        try {
            if (!loadMore)
                _isLoading.postValue(true)
            val response = moviesRepository.getMoviesNowPlaying(page)
            if(response != null && response.isSuccessful) {
                emit(response.body()?.results)
            }
            else {
                if (!loadMore)
                    _hasError.postValue(true)
            }
        } catch (exception: Exception) {
            if (!loadMore)
                _hasError.postValue(true)
        } finally {
            if (!loadMore)
                _isLoading.postValue(false)
        }
    }

    fun getSearchResult(query: String) = liveData(Dispatchers.IO) {
        try {
            _isLoading.postValue(true)
            val response = moviesRepository.getSearchResult(query)
            if(response != null && response.isSuccessful) {
                emit(response.body()?.results)
            } else {
                _hasError.postValue(true)
            }
        } catch (exception: Exception) {
            _hasError.postValue(true)
        } finally {
            _isLoading.postValue(false)
        }
    }

}