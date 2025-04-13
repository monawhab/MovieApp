package com.movieapp.data.remote.api

import com.movieapp.data.remote.api.EndPoints.API_KEY
import com.movieapp.data.remote.api.EndPoints.MOVIES
import com.movieapp.data.remote.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET(MOVIES)
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
    ): Response<MoviesResponse>
}