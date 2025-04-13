package com.movieapp.utils.network

import android.content.Context

sealed class ErrorMessage {
    data class DynamicString(val string: String) : ErrorMessage()
    data class StringResource(val resourceID: Int) : ErrorMessage()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> string
            is StringResource -> context.getString(resourceID)
        }
    }
}