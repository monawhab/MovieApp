package com.movieapp.presentation.movies

import com.movieapp.domain.dto.MovieDto

sealed class MoviesIntents {
    data object GetMovies : MoviesIntents()
    data object RefreshMovies : MoviesIntents()
    data object GetFavMovies : MoviesIntents()
    data class AddMovieToFav(val movieDto: MovieDto) : MoviesIntents()
    data class RemoveMovieFromFav(val movieDto: MovieDto) : MoviesIntents()
}