package com.kotdev.numbersapp.data.api

import android.net.http.HttpException
import android.util.Log
import com.kotdev.numbersapp.core.api.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T> = try {
        val response = request { block() }

        val body = response.body<T>()
        ApiResponse.Success(body)

    } catch (e: Exception) {
        ApiResponse.Error(e.message?:"Something error")
    }