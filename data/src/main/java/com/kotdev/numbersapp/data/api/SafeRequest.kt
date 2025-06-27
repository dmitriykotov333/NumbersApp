package com.kotdev.numbersapp.data.api


import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.core.resource.Resource
import com.kotdev.numbersapp.core.resource.ResourceResolver
import com.kotdev.numbersapp.core_ui.R
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request

suspend inline fun <reified T> HttpClient.safeRequest(
    resolve: ResourceResolver,
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T> = try {
    val response = request { block() }
    val body = response.body<T>()
    ApiResponse.Success(body)

} catch (e: Exception) {
    ApiResponse.Error(e.message ?: resolve.resolve(Resource.String(R.string.something_error)))
}