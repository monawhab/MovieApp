package com.movieapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.flatMap
import com.movieapp.data.local.dao.MoviesDao
import com.movieapp.data.local.entities.MovieEntity
import com.movieapp.data.remote.api.MoviesApi
import com.movieapp.data.remote.source.MoviesRemoteMediator
import com.movieapp.data.remote.source.OnlineMoviePagingSource
import com.movieapp.domain.dto.MovieDto
import com.movieapp.domain.repos.MoviesRepo
import com.movieapp.utils.network.BaseRemoteDataSource
import com.movieapp.utils.network.ErrorTypes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class MoviesRepoImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
) : MoviesRepo, BaseRemoteDataSource() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MoviesRemoteMediator(moviesApi, moviesDao),
            pagingSourceFactory = { moviesDao.getPage() }
        ).flow.map { pagingData ->
            pagingData.flatMap { it.toDto().moviesList }
        }
    }

    override suspend fun getOnlineMovies(
        errors: (ErrorTypes) -> Unit,
        loading: (Boolean) -> Unit
    ) = Pager(
        pagingSourceFactory = {
            OnlineMoviePagingSource(
                moviesDao,
                moviesApi,
                error = { error -> errors(error) },
                loading = { loading -> loading(loading) }
            )
        },
        config = PagingConfig(pageSize = 10, enablePlaceholders = false)
    ).flow

    override suspend fun getOfflineMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            pagingSourceFactory = { moviesDao.getPage() },
            config = PagingConfig(pageSize = 10, enablePlaceholders = false)
        ).flow.map { pagingData ->
            pagingData.flatMap { pageEntity ->
                pageEntity.toDto().moviesList
            }
        }
    }

    override suspend fun deleteAllMovies() {
        moviesDao.deleteAllPages()
    }

    override suspend fun hasStoredPages() = moviesDao.getPageCount() > 0

    override suspend fun getAllFav(): Flow<List<MovieDto>> {
        return flowOf(moviesDao.getFavMovies().map { movieEntity -> movieEntity.toMovieDto() })
    }

    override suspend fun addFav(movie: MovieDto) {
        val movieEntity = MovieEntity(
            movieId = movie.movieId,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            voteCount = movie.voteCount,
            voteAverage = movie.voteAverage,
            genresIds = movie.genreIds,
            isFav = true,
            date = movie.date
        )
        moviesDao.addFav(movieEntity)
    }

    override suspend fun removeFav(movieId: Int) {
        moviesDao.deleteFav(movieId)
    }

}