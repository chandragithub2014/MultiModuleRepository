package com.movie.movieinfo.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hilt.demo.repository.NetworkRepository
import com.movie.core.roomDB.Movie
import com.movie.info.model.MovieModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDataViewModel @ViewModelInject constructor(private val dispatcher: CoroutineDispatcher, private val networkRepository: NetworkRepository) :
    ViewModel(), LifecycleObserver {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    var movieListMutableLiveData = MutableLiveData<MovieModel>()
    var movieListFromLocal: LiveData<MutableList<Movie>> = MutableLiveData<MutableList<Movie>>()
    var movieInfo  = MutableLiveData<Movie>()

    fun fetchMovieData() {
        loading.postValue(true)
        viewModelScope.launch(dispatcher) {
            try {
                val response = networkRepository.fetchMovies()
                if (response.isSuccessful) {
                    loading.postValue(false)
                    errorOnAPI.postValue("")
                    insertDataToLocalDB(response.body())

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

    private fun  prepareMovieDataForLocalDB(movieModel: MovieModel?) : MutableList<Movie>{
        var movieList = mutableListOf<Movie>()
        movieModel?.let {
            val results = movieModel.totalResults
            val response = movieModel.Response
            val searchResults = movieModel.Search

            for(i in searchResults){
                val movie = Movie(i.Poster,i.Title,i.Type,i.Year,i.imdbID,false,response,results)
                movieList.add(movie)
            }
        }
        return  movieList
    }

    private fun insertDataToLocalDB(movieModel: MovieModel?){
        viewModelScope.launch(dispatcher)  {
            try{
                val movieList = prepareMovieDataForLocalDB(movieModel)
                networkRepository.insertToLocalDB(movieList)
                fetchMovieDataByTitleASC()
            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }

        }
    }

    fun fetchMovieDataByTitleASC(){

            try {
                movieListFromLocal = networkRepository.fetchMoviesFromLocal()
                loading.postValue(false)
                errorOnAPI.postValue("")
            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }

    }

    fun fetchMovieDataByTitleDesc(){

        try {
            movieListFromLocal = networkRepository.fetchMoviesSortByTitleDesc()
            loading.postValue(false)
            errorOnAPI.postValue("")
        }catch (e:Exception){
            e.printStackTrace()
            loading.postValue(false)
            errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
        }

    }


    fun fetchMovieDataByTypeASC(){

        try {
            movieListFromLocal = networkRepository.fetchMoviesSortByTypeAsc()
            loading.postValue(false)
            errorOnAPI.postValue("")
        }catch (e:Exception){
            e.printStackTrace()
            loading.postValue(false)
            errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
        }

    }

    fun fetchMovieDataByImdbASC(){

        try {
            movieListFromLocal = networkRepository.fetchMoviesSortByImdbAsc()
            loading.postValue(false)
            errorOnAPI.postValue("")
        }catch (e:Exception){
            e.printStackTrace()
            loading.postValue(false)
            errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
        }

    }

    fun fetchMovieDataByYearASC(){

        try {
            movieListFromLocal = networkRepository.fetchMoviesSortByYearAsc()
            loading.postValue(false)
            errorOnAPI.postValue("")
        }catch (e:Exception){
            e.printStackTrace()
            loading.postValue(false)
            errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
        }

    }

     fun fetchMovieById(imdb : String){
        viewModelScope.launch(dispatcher) {
            try {
                val movieInfo_detail = networkRepository.fetchMovieInfoBasedOnImdbId(imdb)
                println("Movie Info For Imdb_Id ${movieInfo_detail}")
                movieInfo.postValue(movieInfo_detail)
                loading.postValue(false)
                errorOnAPI.postValue("")
            } catch (e: Exception) {
                e.printStackTrace()
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }
        }
    }

     fun updateFavouriteMovie(favourite : Boolean, imdbId:String){
         viewModelScope.launch(dispatcher) {
             try {
              val updated_id = networkRepository.updateFavoriteMovieForImdb_id(favourite,imdbId)
                 if(updated_id > 0){
                     loading.postValue(false)
                     errorOnAPI.postValue("")
                 }else{
                     loading.postValue(false)
                     errorOnAPI.postValue("Update Failed")
                 }
             }catch (e: Exception) {
                 e.printStackTrace()
                 loading.postValue(false)
                 errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
             }
         }
     }
    fun fetchError(): LiveData<String> = errorOnAPI
    fun fetchLoadStatus(): LiveData<Boolean> = loading
    fun fetchMoviesLiveData(): LiveData<MovieModel> = movieListMutableLiveData
    fun fetchMovieDetail():LiveData<Movie> = movieInfo

}