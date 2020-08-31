package com.movie.core.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(

    @ColumnInfo(name = "poster")
    val Poster: String,
    @ColumnInfo(name = "title")
    val Title: String,
    @ColumnInfo(name = "type")
    val Type: String,
    @ColumnInfo(name = "year")
    val Year: String,
    @PrimaryKey
    @ColumnInfo(name = "imbdb")
    val imdbID: String,
    @ColumnInfo(name = "favourite")
    val favourite: Boolean,
    @ColumnInfo(name = "response")
    val Response: String,
    @ColumnInfo(name = "total_results")
    val totalResults: String

)