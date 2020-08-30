package com.movie.info.dimodules

import com.hilt.demo.repository.CoroutineHelper
import com.hilt.demo.repository.NetworkRepository
import com.movie.common.util.NetworkUtil
import com.movie.core.roomDB.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkRepositoryModule {

    @Provides
    fun provideRepository(apiService: NetworkAPIService,movieDao: MovieDao) = NetworkRepository(apiService,movieDao)

    @Provides
    fun provideCoroutineDispatcher() = CoroutineHelper().fetchCoroutineDispatcher()


    @Provides
    fun provideAPIService(retrofit: Retrofit) = retrofit.create(NetworkAPIService::class.java)


}