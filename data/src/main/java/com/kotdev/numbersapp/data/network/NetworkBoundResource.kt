package com.kotdev.numbersapp.data.network

import com.kotdev.numbersapp.core.api.StatusRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) = channelFlow {
    val loading = launch { query().collect { send(StatusRequest.InProgress(it)) }
    }

    try {
        saveFetchResult(fetch())
        loading.cancel()
        query().collect { send(StatusRequest.Success(it)) }
    } catch (t: Throwable) {
        loading.cancel()
        query().collect { send(StatusRequest.Error(t.message.toString(), it)) }
    }
}