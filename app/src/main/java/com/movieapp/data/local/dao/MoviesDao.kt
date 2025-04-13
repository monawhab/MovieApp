package com.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movieapp.data.local.entities.MovieEntity
import com.movieapp.data.local.entities.PageEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentPage(pageEntity: PageEntity)

    @Query("SELECT * FROM pages ORDER BY pageNumber ASC")
    fun getPage(): PagingSource<Int, PageEntity>

    @Query("DELETE FROM pages")
    suspend fun deleteAllPages()

    @Query("SELECT COUNT(*) FROM pages")
    suspend fun getPageCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(movie: MovieEntity) //update and insert

    @Query("DELETE FROM MovieEntity WHERE movieId = :movieID")
    suspend fun deleteFav(movieID: Int)

    @Query("SELECT * FROM MovieEntity WHERE isFav = 1")
    fun getFavMovies() : List<MovieEntity>
}