package com.movieapp.domain.dto

import java.io.Serializable

data class MovieDto(
    val currentPageId: Int? = null,
    val movieId: Int?,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val voteCount: Int?,
    val voteAverage: Double?,
    val date: String?,
    val isFav: Boolean = false,
    val genreIds: List<Int>?
) : Serializable
