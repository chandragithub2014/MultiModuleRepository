package com.hilt.demo.repository


import com.movie.common.util.NetworkUtil
import com.movie.core.roomDB.Movie
import com.movie.core.roomDB.MovieDao
import com.movie.info.dimodules.NetworkAPIService
import com.movie.info.model.MovieModel
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: NetworkAPIService,private val movieDao: MovieDao) {

    suspend fun fetchMovies(): Response<MovieModel> = apiService.fetchMovieInfo(NetworkUtil.API_KEY,NetworkUtil.MOVIE_NAME)
    fun fetchMoviesFromLocal() = movieDao.fetchByTitleAsc()
    fun fetchMoviesSortByTypeAsc() = movieDao.fetchByTypeAsc()
    fun fetchMoviesSortByImdbAsc() = movieDao.fetchByImdbAsc()
    fun fetchMoviesSortByYearAsc() = movieDao.fetchByYearAsc()
    fun fetchMoviesSortByTitleDesc() = movieDao.fetchByTitleDesc()
    suspend fun insertToLocalDB(movieInfo : MutableList<Movie>) {
        movieDao.insertMovieInfo(movieInfo)
    }
}
