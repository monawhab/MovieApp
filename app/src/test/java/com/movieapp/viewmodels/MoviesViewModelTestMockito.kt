package com.movieapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.movieapp.MainCoroutineRule
import com.movieapp.domain.repos.MoviesRepo
import com.movieapp.getOrAwaitValueTest
import com.movieapp.presentation.movies.MoviesViewModel
import com.movieapp.presentation.movies.MoviesViewStates
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class MoviesViewModelTestMockito {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: MoviesRepo
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        repository = Mockito.mock(MoviesRepo::class.java)
        viewModel = MoviesViewModel(repository)
    }

    @Test
    fun `test getOnlineMovies fetch movies from online source`() = runTest {

        // Tell repo to return the mock page
        Mockito.`when`(
            repository.getOnlineMovies(errors = {}, loading = {})
        ).thenReturn(
            flowOf(PagingData.empty())
        )

        // Act
        viewModel.getOnlineMovies(errors = {}, loading = {})

        // Ensure that all coroutines is executed.
        advanceUntilIdle()

        // Assert
        val value = viewModel.result.getOrAwaitValueTest()
        assertThat(value).isEqualTo(MoviesViewStates.MoviesList(PagingData.empty()))
    }
}