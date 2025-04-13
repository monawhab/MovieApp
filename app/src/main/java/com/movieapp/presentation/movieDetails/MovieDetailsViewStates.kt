package com.movieapp.presentation.movieDetails

import com.movieapp.domain.dto.MovieDto

sealed class MovieDetailsViewStates {
    data class MovieDetails(val movie: MovieDto) : MovieDetailsViewStates()
}