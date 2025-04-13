package com.movieapp.data.remote.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.movieapp.data.local.dao.MoviesDao
import com.movieapp.data.local.entities.MovieEntity
import com.movieapp.data.local.entities.PageEntity
import com.movieapp.data.remote.api.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao
) : RemoteMediator<Int, PageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PageEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Find last page index from DB or infer from state
                    val lastPage = moviesDao.getPageCount() ?: 1
                    lastPage + 1
                }
            }

            val response = moviesApi.getMovies(page = page)
            val result = response.body()

            val localMovies = withContext(Dispatchers.IO) {
                moviesDao.getFavMovies()
            }

            // Merge the data: Map local movies to preserve 'isFav'
            val moviesWithFavStatus = result?.moviesList?.map { networkMovie ->
                val localMovie = localMovies.find { it.movieId == networkMovie.id }
                if (localMovie != null) {
                    // Preserve the 'isFav' status from the local database
                    networkMovie.copy(isFav = localMovie.isFav)
                } else {
                    networkMovie
                }
            }

            if (result != null) {
                val entity = PageEntity.fromDto(page, moviesWithFavStatus!!.map { movie ->
                    val movieDto = movie.toMovieDto()
                    MovieEntity(
                        movieId = movieDto.movieId,
                        title = movieDto.title,
                        overview = movieDto.overview,
                        posterPath = movieDto.posterPath,
                        voteCount = movieDto.voteCount,
                        voteAverage = movieDto.voteAverage,
                        genresIds = movieDto.genreIds,
                        isFav = movieDto.isFav,
                        date = movieDto.date
                    )
                })

                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteAllPages()
                }
                moviesDao.saveCurrentPage(entity)
                return MediatorResult.Success(endOfPaginationReached = moviesWithFavStatus.isEmpty())
            }

            return MediatorResult.Error(Throwable("Empty response"))
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

}
