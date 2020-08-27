package com.movie.info.model

data class MovieModel(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)