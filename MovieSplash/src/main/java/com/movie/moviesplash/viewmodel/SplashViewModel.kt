package com.movie.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movie.app.utils.SplashUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class SplashViewModel : ViewModel() {

    var isSplashCompleted: MutableLiveData<Boolean> = MutableLiveData()
    var compositeDisposable = CompositeDisposable()
    init {
        compositeDisposable.add(
            Observable.timer(SplashUtils.SPLASH_DELAY, TimeUnit.SECONDS)
            .subscribe {
                isSplashCompleted.postValue(true)
            })

    }

    fun isSplashCompleted(): LiveData<Boolean> = isSplashCompleted

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}