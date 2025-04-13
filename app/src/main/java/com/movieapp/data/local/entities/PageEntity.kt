package com.movieapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieapp.domain.dto.CurrentPageDto

@Entity(tableName = "pages")
data class PageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "pageNumber")
    val pageNumber: Int,
    @ColumnInfo(name = "moviesList")
    val moviesList: List<MovieEntity>
) {
    fun toDto(): CurrentPageDto {
        return CurrentPageDto(
            page = pageNumber,
            moviesList = moviesList.map { it.toMovieDto() }
        )
    }

    companion object {
        fun fromDto(page: Int, moviesList: List<MovieEntity>): PageEntity {
            return PageEntity(
                pageNumber = page,
                moviesList = moviesList
            )
        }
    }
}