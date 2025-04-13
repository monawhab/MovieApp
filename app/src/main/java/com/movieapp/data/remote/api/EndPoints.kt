package com.movieapp.data.remote.api

import com.movieapp.BuildConfig

object EndPoints {
    const val API_KEY = BuildConfig.API_KEY
    const val BEAR_TOKEN = "Bearer ${BuildConfig.BEAR_TOKEN}"

    const val BASE_URL = BuildConfig.BASE_URL
    const val IMAGES_BASE_URL = BuildConfig.IMAGES_BASE_URL

    const val MOVIES = "movie/popular"
}