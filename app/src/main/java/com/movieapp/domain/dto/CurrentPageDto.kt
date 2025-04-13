package com.movieapp.domain.dto

data class CurrentPageDto(
    val page: Int,
    val moviesList: List<MovieDto>
)
