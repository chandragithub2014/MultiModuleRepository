package com.movie.core.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {

    @Query("select * From movie ORDER BY title ASC")
     fun  fetchByTitleAsc() : LiveData<MutableList<Movie>>

    @Query("select * From movie ORDER BY title DESC")
    fun  fetchByTitleDesc() : LiveData<MutableList<Movie>>

    @Query("select * From movie ORDER BY type ASC")
    fun  fetchByTypeAsc() : LiveData<MutableList<Movie>>


    @Query("select * From movie ORDER BY type DESC")
    fun  fetchByTypeDesc() : LiveData<MutableList<Movie>>

    @Query("select * From movie ORDER BY imbdb ASC")
    fun  fetchByImdbAsc() : LiveData<MutableList<Movie>>


    @Query("select * From movie ORDER BY imbdb DESC")
    fun  fetchByImdbDesc() : LiveData<MutableList<Movie>>


    @Query("select * From movie ORDER BY year ASC")
    fun  fetchByYearAsc() : LiveData<MutableList<Movie>>


    @Query("select * From movie ORDER BY year DESC")
    fun  fetchByYearDesc() : LiveData<MutableList<Movie>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend   fun insertMovieInfo(movieList: MutableList<Movie>)
}