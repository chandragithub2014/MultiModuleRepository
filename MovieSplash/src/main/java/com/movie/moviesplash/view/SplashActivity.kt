package com.movie.moviesplash.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.movie.app.viewmodel.SplashViewModel
import androidx.lifecycle.Observer
import com.movie.moviesplash.R
import com.mvvm.appnavigator.openActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        private val TAG: String? = SplashActivity::class.simpleName
    }
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observeViewModel()
    }

    private fun observeViewModel() {
        splashViewModel.isSplashCompleted().observe(this, Observer {
            if(it){
                Log.d(TAG,"Splash Screen Completed..")
                launchMovieInfoModule()
            }
        })
    }

    private fun launchMovieInfoModule(){
        this?.openActivity(Class.forName("com.movie.movieinfo.view.MovieInfoActivity"))
    }
}