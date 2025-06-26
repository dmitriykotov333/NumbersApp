package com.kotdev.numbersapp.core.api

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend inline fun <reified T> wrapper(
    request: suspend () -> ApiResponse<T>,
    cache: suspend (T) -> Unit,
    status: (StatusRequest<T>) -> Unit,
) {
    try {
        status.invoke(StatusRequest.InProgress())
        val response = request.invoke().prepare()
        cache(response.data!!)
        status.invoke(StatusRequest.Success(response.data))
    } catch (t: Throwable) {
        status.invoke(StatusRequest.Error(t.message.toString()))
    }
}
