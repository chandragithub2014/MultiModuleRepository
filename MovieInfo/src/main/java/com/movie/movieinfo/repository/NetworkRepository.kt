package com.hilt.demo.repository


import com.movie.common.util.NetworkUtil
import com.movie.info.dimodules.NetworkAPIService
import com.movie.info.model.MovieModel
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: NetworkAPIService) {

    suspend fun fetchMovies(): Response<MovieModel> = apiService.fetchMovieInfo(NetworkUtil.API_KEY,NetworkUtil.MOVIE_NAME)
}
