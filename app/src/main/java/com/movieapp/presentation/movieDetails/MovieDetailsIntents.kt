package com.movieapp.presentation.movieDetails

sealed class MovieDetailsIntents {
    data object AddMovieToFav : MovieDetailsIntents()
    data object RemoveMovieFromFav : MovieDetailsIntents()
}