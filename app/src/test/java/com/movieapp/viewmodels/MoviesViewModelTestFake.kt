package com.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.movieapp.MainCoroutineRule
import com.movieapp.getOrAwaitValueTest
import com.movieapp.presentation.movies.MoviesViewModel
import com.movieapp.presentation.movies.MoviesViewStates
import com.movieapp.repo.MoviesFakeRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MoviesViewModelTestFake {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(MoviesFakeRepo())
    }

    @Test
    fun `test getMovies success`() = runTest {
        // Act
        viewModel.getMovies()

        advanceUntilIdle()

        // Assert
        val value = viewModel.result.getOrAwaitValueTest()
        assertThat(value).isEqualTo(MoviesViewStates.MoviesList(PagingData.empty()))
    }

//    @Test
//    fun `test getOnlineMovies fetch movies from online source`() = runTest {
//        // Act
//        viewModel.getOnlineMovies(
//            errors = {},
//            loading = {}
//        )
//
//        // Ensure that all coroutines is executed.
//        advanceUntilIdle()
//
//        // Assert
//        val value = viewModel.result.getOrAwaitValueTest()
//        assertThat(value).isEqualTo(MoviesViewStates.MoviesList(PagingData.empty()))
//    }
}