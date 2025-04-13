package com.movieapp.data.remote.models

import com.movieapp.domain.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val moviesList: List<Movie>
)

data class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val isFav: Boolean = false,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toMovieDto(): MovieDto {
        return MovieDto(
            movieId = id,
            title = originalTitle,
            overview = overview,
            posterPath = posterPath,
            voteCount = voteCount,
            voteAverage = voteAverage,
            genreIds = genreIds,
            isFav = isFav,
            date = releaseDate
        )
    }
}