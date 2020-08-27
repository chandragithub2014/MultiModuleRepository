package com.movie.movieinfo.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hilt.demo.repository.NetworkRepository
import com.movie.info.model.MovieModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDataViewModel @ViewModelInject constructor(private val dispatcher: CoroutineDispatcher, private val networkRepository: NetworkRepository) :
    ViewModel(), LifecycleObserver {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    var movieListMutableLiveData = MutableLiveData<MovieModel>()

    fun fetchMovieData() {

        viewModelScope.launch(dispatcher) {
            try {
                val response = networkRepository.fetchMovies()
                if (response.isSuccessful) {
                    movieListMutableLiveData.postValue(response.body())
                    loading.postValue(false)
                    } else {
                    loading.postValue(false)
                    errorOnAPI.postValue("Something went wrong::${response.message()}")
                    }
                } catch (e: Exception) {
                e.printStackTrace()
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
                } finally {
                }
            }
        }

    fun fetchError(): LiveData<String> = errorOnAPI
    fun fetchLoadStatus(): LiveData<Boolean> = loading
    fun fetchMoviesLiveData(): LiveData<MovieModel> = movieListMutableLiveData


}