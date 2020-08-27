package com.movie.info.dimodules


import com.movie.info.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkAPIService {

    @GET(".")
    suspend fun fetchMovieInfo(@Query("apikey")apiKey : String, @Query("s")movieName : String): Response<MovieModel>
}