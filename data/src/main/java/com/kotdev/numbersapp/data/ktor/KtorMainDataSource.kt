package com.kotdev.numbersapp.data.ktor

import android.util.Log
import com.kotdev.numbersapp.core.api.ApiResponse
import com.kotdev.numbersapp.core.api.prepare
import com.kotdev.numbersapp.core.resource.ResourceResolver
import com.kotdev.numbersapp.data.api.safeRequest
import com.kotdev.numbersapp.data.extensions.toResult
import com.kotdev.numbersapp.domain.entities.FactData
import com.kotdev.numbersapp.domain.entities.StatusResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class KtorMainDataSource(
    private val httpClient: HttpClient,
    private val resolver: ResourceResolver
) {

    suspend fun math(isRandom: Boolean = false, value: Int = -1): ApiResponse<FactData> {
        val rst = httpClient.safeRequest<FactData>(
            resolver
        ) {
            url {
                method = HttpMethod.Get
                if (!isRandom) {
                    path("$value/math")
                } else {
                    path("random/math")
                }

            }
        }
        return rst.toResult()
    }

    suspend fun trivia(isRandom: Boolean = false, value: Int = -1): ApiResponse<FactData> {
        val rst = httpClient.safeRequest<FactData>(
            resolver
        ) {
            url {
                method = HttpMethod.Get
                if (!isRandom) {
                    path("$value")
                } else {
                    path("random/trivia")
                }
            }
        }
        return rst.toResult()
    }

    suspend fun year(isRandom: Boolean = false, value: Int = -1): ApiResponse<FactData> {
        val rst = httpClient.safeRequest<FactData>(
            resolver
        ) {
            url {
                method = HttpMethod.Get
                if (!isRandom) {
                    path("$value/year")
                } else {
                    path("random/year")
                }
            }
        }
        return rst.toResult()
    }

    suspend fun date(
        isRandom: Boolean = false,
        value1: Int = -1,
        value2: Int = -1
    ): ApiResponse<FactData> {
        val rst = httpClient.safeRequest<FactData>(
            resolver
        ) {
            url {
                method = HttpMethod.Get
                if (!isRandom) {
                    path("$value1/$value2/date")
                } else {
                    path("random/date")
                }
            }
        }

        return rst.toResult()
    }
}