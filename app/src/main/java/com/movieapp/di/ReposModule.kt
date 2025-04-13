package com.movieapp.di

import com.movieapp.data.local.dao.MoviesDao
import com.movieapp.data.remote.api.MoviesApi
import com.movieapp.data.MoviesRepoImpl
import com.movieapp.domain.repos.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReposModule {

    @Provides
    @Singleton
    fun provideMoviesRepo(
        moviesApi: MoviesApi,
        moviesDao: MoviesDao
    ): MoviesRepo {
        return MoviesRepoImpl(moviesApi, moviesDao)
    }
}