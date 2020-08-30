package com.movie.multimodulemovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mvvm.appnavigator.openActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this?.openActivity(Class.forName("com.movie.moviesplash.view.SplashActivity"))
        finish()
    }
}