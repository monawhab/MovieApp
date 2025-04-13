package com.movieapp.utils.ui

import androidx.lifecycle.ViewModel
import com.movieapp.utils.SingleMutableLiveData
import com.movieapp.utils.network.ErrorMessage
import com.movieapp.utils.network.ErrorTypes

open class BaseViewModel : ViewModel() {

    private val _errorMessage: SingleMutableLiveData<ErrorMessage> = SingleMutableLiveData()
    val errorMessage get() = _errorMessage

    private val _isLoading: SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val isLoading get() = _isLoading

    private val _isAuthError: SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val isAuthError get() = _isAuthError

    fun handleError(errorTypes: ErrorTypes) {
        _isLoading.value = false
        when (errorTypes) {
            is ErrorTypes.AuthenticationError -> _isAuthError.value = true

            else -> {
                _errorMessage.value = errorTypes.errorMessage
            }
        }
    }

    fun handleLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}