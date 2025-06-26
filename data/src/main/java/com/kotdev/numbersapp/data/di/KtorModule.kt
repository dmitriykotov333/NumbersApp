package com.kotdev.numbersapp.data.di

import com.kotdev.numbersapp.core.BuildConfig
import com.kotdev.numbersapp.data.FactRandomRepository
import com.kotdev.numbersapp.data.FactRepositoryImpl
import com.kotdev.numbersapp.data.ktor.KtorMainDataSource
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.domain.repositories.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors(maxRetries = 1)
                exponentialDelay()
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 8_000
                requestTimeoutMillis = 12_000
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BuildConfig.BASE_URL)
            }
        }
    }

    @Singleton
    @Provides
    fun provideKtorDataSource(
        httpClient: HttpClient
    ) = KtorMainDataSource(httpClient)

    @Singleton
    @Provides
    fun provideFactRepository(
        remote: KtorMainDataSource,
        db: FactDatabase
    ): FactRepository = FactRepositoryImpl(remote, db)

    @Singleton
    @Provides
    fun provideFactRandomRepository(
        remote: KtorMainDataSource,
        db: FactDatabase
    ) = FactRandomRepository(remote, db.factDao)


}