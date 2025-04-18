package com.movieapp.repo

import androidx.paging.PagingData
import com.movieapp.domain.dto.MovieDto
import com.movieapp.domain.repos.MoviesRepo
import com.movieapp.utils.network.ErrorTypes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MoviesFakeRepo : MoviesRepo {
    override fun getMovies(): Flow<PagingData<MovieDto>> {
        return flowOf(PagingData.empty()) // Emits immediately for testing
    }

    override suspend fun getOnlineMovies(
        errors: (ErrorTypes) -> Unit,
        loading: (Boolean) -> Unit
    ): Flow<PagingData<MovieDto>> {
        return flowOf(PagingData.empty())
    }

    override suspend fun getOfflineMovies(): Flow<PagingData<MovieDto>> {
        return flowOf(PagingData.empty())
    }

    override suspend fun deleteAllMovies() {
        // Not need to test it here
        // I will test dao itself.
    }

    override suspend fun hasStoredPages() = true
    override suspend fun getAllFav(): Flow<List<MovieDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFav(movie: MovieDto) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFav(movieId: Int) {
        TODO("Not yet implemented")
    }
}