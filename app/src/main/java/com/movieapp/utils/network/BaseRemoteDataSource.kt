package com.movieapp.utils.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Response

open class BaseRemoteDataSource {
    fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
    ): Flow<Resource<T>> = flow {
        emit(safeApiResult(call))
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Resource<T> {
        var response: Response<T>? = null

        try {
            response = call.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            return getResultError(response)
        }

        if (response.isSuccessful) {
            return Resource.Success(response.body())
        }

        return getResultError(response)
    }

    private fun <T : Any> getResultError(response: Response<T>?): Resource<T> {
        return when (response?.code()) {
            401 -> {
                Resource.Error(ErrorTypes.AuthenticationError())
            }

            in 402..499 -> {
                val message: String = try {
                    val jObjError = JSONObject(response?.errorBody()?.string().orEmpty())
                    jObjError.getString("message").orEmpty()
                } catch (e: Exception) {
                    "Error happened, try again."
                }

                Resource.Error(
                    ErrorTypes.NetworkError(
                        ErrorMessage.DynamicString(message)
                    )
                )
            }

            500 -> {
                Resource.Error(
                    ErrorTypes.NetworkError(ErrorMessage.DynamicString("Opps, unknown error happened, please try again later"))
                )
            }

            else -> {
                val message = response?.errorBody()?.string().orEmpty()
                if (message.isEmpty()) {
                    Resource.Error(
                        ErrorTypes.GeneralError(
                            ErrorMessage.DynamicString("Unknown error, please check your internet and try again.")
                        )
                    )
                } else {
                    Resource.Error(ErrorTypes.GeneralError(ErrorMessage.DynamicString(message)))
                }
            }
        }
    }
}