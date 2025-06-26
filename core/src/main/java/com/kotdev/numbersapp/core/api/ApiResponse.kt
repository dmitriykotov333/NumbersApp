package com.kotdev.numbersapp.core.api

import android.util.Log


sealed class ApiResponse<out T>() {

    data class Success<T>(val data: T) : ApiResponse<T>()

    data class Error(
        val message: String?,
    ) : ApiResponse<Nothing>()

}

fun <T> ApiResponse<T>.prepare() = when (this) {
    is ApiResponse.Success -> {
        ApiResponse.Success(this.data)
    }

    is ApiResponse.Error -> {
        throw Throwable(this.message)
    }
}