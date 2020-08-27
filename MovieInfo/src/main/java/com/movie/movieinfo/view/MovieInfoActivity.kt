package com.movie.movieinfo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.movie.movieinfo.R
import com.mvvm.appnavigator.replaceFragmentWithNoHistory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        replaceFragmentWithNoHistory(MovieListFragment(), R.id.container_fragment)
    }
}