package com.movieapp.data.local.db

import androidx.room.TypeConverter
import com.movieapp.data.local.entities.MovieEntity
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMovieListToJson(movies: List<MovieEntity>): String {
        return gson.toJson(movies)
    }

    @TypeConverter
    fun fromJsonToMovieList(json: String): List<MovieEntity> {
        val type = object : com.google.gson.reflect.TypeToken<List<MovieEntity>>() {}.type
        return gson.fromJson(json, type)
    }


    @TypeConverter
    fun fromGenresType(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toGenresType(data: String?): List<Int>? {
        return data?.split(",")?.mapNotNull { it.toIntOrNull() }
    }
}