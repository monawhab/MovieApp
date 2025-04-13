package com.movieapp.domain.repos

import androidx.paging.PagingData
import com.movieapp.domain.dto.MovieDto
import com.movieapp.utils.network.ErrorTypes
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {

    fun getMovies(): Flow<PagingData<MovieDto>>

    suspend fun getOnlineMovies(
        errors: (ErrorTypes) -> Unit,
        loading: (Boolean) -> Unit,
    ): Flow<PagingData<MovieDto>>

    suspend fun getOfflineMovies(): Flow<PagingData<MovieDto>>

    suspend fun deleteAllMovies()

    suspend fun hasStoredPages(): Boolean

    suspend fun getAllFav(): Flow<List<MovieDto>>

    suspend fun addFav(movie: MovieDto)

    suspend fun removeFav(movieId: Int)
}