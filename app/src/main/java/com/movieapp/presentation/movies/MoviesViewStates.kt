package com.movieapp.presentation.movies

import androidx.paging.PagingData
import com.movieapp.domain.dto.MovieDto

sealed class MoviesViewStates {
    data class MoviesList(val movies: PagingData<MovieDto>) : MoviesViewStates()
}