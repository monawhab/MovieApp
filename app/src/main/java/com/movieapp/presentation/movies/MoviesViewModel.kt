package com.movieapp.presentation.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.movieapp.domain.dto.MovieDto
import com.movieapp.domain.repos.MoviesRepo
import com.movieapp.utils.SingleMutableLiveData
import com.movieapp.utils.network.ErrorMessage
import com.movieapp.utils.network.ErrorTypes
import com.movieapp.utils.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepo: MoviesRepo) : BaseViewModel() {

    val isGrid = false

    private val sendIntend: MutableSharedFlow<MoviesIntents> = MutableSharedFlow()

    private val _result: SingleMutableLiveData<MoviesViewStates> = SingleMutableLiveData()
    val result: SingleMutableLiveData<MoviesViewStates> get() = _result

    private val cachedMoviesFlow: Flow<PagingData<MovieDto>> by lazy {
        moviesRepo.getMovies().cachedIn(viewModelScope)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(ErrorTypes.GeneralError(ErrorMessage.DynamicString(throwable.message.orEmpty())))
    }

    fun sendIntend(intent: MoviesIntents) = viewModelScope.launch {
        sendIntend.emit(intent)
    }

    init {
        viewModelScope.launch {
            sendIntend.collect {
                when (it) {
                    MoviesIntents.GetMovies -> getMovies()
                    MoviesIntents.RefreshMovies -> {
                        deleteAllMovies()
                        getMovies()
                    }

                    MoviesIntents.GetFavMovies -> TODO()
                    is MoviesIntents.AddMovieToFav -> addToFav(it.movieDto)
                    is MoviesIntents.RemoveMovieFromFav -> removeFromFav(it.movieDto.movieId!!)
                }
            }
        }
    }

    fun getMovies() = viewModelScope.launch(coroutineExceptionHandler) {
        cachedMoviesFlow.collect {
            _result.value = MoviesViewStates.MoviesList(it)
        }
    }

    suspend fun getOnlineMovies(
        errors: (ErrorTypes) -> Unit,
        loading: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        moviesRepo.getOnlineMovies(errors = { errors(it) }, loading = { loading(it) })
    }

    private suspend fun deleteAllMovies() = withContext(Dispatchers.IO) {
        moviesRepo.deleteAllMovies()
    }


    private fun addToFav(movie: MovieDto) = viewModelScope.launch(coroutineExceptionHandler) {
        moviesRepo.addFav(movie)
        updateFavorite(movie.movieId!!, true)
    }

    private fun removeFromFav(movieId: Int) = viewModelScope.launch(coroutineExceptionHandler) {
        moviesRepo.removeFav(movieId)
        updateFavorite(movieId, false)
    }

    private fun updateFavorite(movieId: Int, isFav: Boolean) {
        val currentValue = _result.value

        if (currentValue is MoviesViewStates.MoviesList) {
            viewModelScope.launch {
                val newPagingData = currentValue.movies.map { movie ->
                    if (movie.movieId == movieId) movie.copy(isFav = isFav)
                    else movie
                }

                _result.postValue(MoviesViewStates.MoviesList(newPagingData))
            }
        }
    }

}