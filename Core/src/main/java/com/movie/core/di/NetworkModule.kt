package com.movie.common.di

import android.content.Context
import com.movie.common.util.NetworkUtil
import com.movie.core.roomDB.MovieDB
import com.movie.core.roomDB.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideNetworkURL(): String = NetworkUtil.NETWORK_BASE_URL

    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOKHttp(logger: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        with(okHttpClient) {
            addInterceptor(logger)
            callTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }
        return okHttpClient.build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient) = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideMovieDao(@ApplicationContext appContext: Context) : MovieDao {
        return MovieDB.getInstance(appContext).movieDao
    }
}