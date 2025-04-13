package com.movieapp.utils.network



sealed class ErrorTypes(open val errorMessage: ErrorMessage) {

    class ConnectError(override val errorMessage: ErrorMessage = ErrorMessage.DynamicString("Check your internet connection and try again!")) :
        ErrorTypes(errorMessage)

    class AuthenticationError(override val errorMessage: ErrorMessage = ErrorMessage.DynamicString("Auth error happened, please logout and login agan!")) :
        ErrorTypes(errorMessage)

    class NoData(override val errorMessage: ErrorMessage = ErrorMessage.DynamicString("No data found!")) :
        ErrorTypes(errorMessage)

    class GeneralError(override val errorMessage: ErrorMessage) : ErrorTypes(errorMessage)

    class NetworkError(override val errorMessage: ErrorMessage) : ErrorTypes(errorMessage)
}