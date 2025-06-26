package com.kotdev.numbersapp.core.api

sealed class StatusRequest<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Error<T>(message: String, data: T? = null) : StatusRequest<T>(data, message)
    class Success<T>(data: T) : StatusRequest<T>(data)
    class InProgress<T>(data: T? = null) : StatusRequest<T>(data)
}