package com.movieapp.presentation.movieDetails

import androidx.lifecycle.viewModelScope
import com.movieapp.domain.dto.MovieDto
import com.movieapp.domain.repos.MoviesRepo
import com.movieapp.utils.SingleMutableLiveData
import com.movieapp.utils.network.ErrorMessage
import com.movieapp.utils.network.ErrorTypes
import com.movieapp.utils.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val moviesRepo: MoviesRepo) : BaseViewModel() {

    private val sendIntend: MutableSharedFlow<MovieDetailsIntents> = MutableSharedFlow()

    var movie: SingleMutableLiveData<MovieDetailsViewStates> = SingleMutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(ErrorTypes.GeneralError(ErrorMessage.DynamicString(throwable.message.orEmpty())))
    }

    fun sendIntend(intent: MovieDetailsIntents) = viewModelScope.launch {
        sendIntend.emit(intent)
    }

    init {
        viewModelScope.launch {
            sendIntend.collect {
                when (it) {
                    MovieDetailsIntents.AddMovieToFav -> addToFav((movie.value as MovieDetailsViewStates.MovieDetails).movie)
                    MovieDetailsIntents.RemoveMovieFromFav -> {
                        removeFromFav((movie.value as MovieDetailsViewStates.MovieDetails).movie.movieId ?: 0)
                    }
                }
            }
        }
    }

    private fun addToFav(movie: MovieDto) = viewModelScope.launch(coroutineExceptionHandler) {
        moviesRepo.addFav(movie)
    }

    private fun removeFromFav(movieId: Int) = viewModelScope.launch(coroutineExceptionHandler) {
        moviesRepo.removeFav(movieId)
    }
}