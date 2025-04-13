package com.movieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.movieapp.presentation.movieDetails.MovieDetailsFragment
import com.movieapp.presentation.movies.MoviesFragment
import javax.inject.Inject

class MoviesFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MoviesFragment::class.java.name -> MoviesFragment()
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}