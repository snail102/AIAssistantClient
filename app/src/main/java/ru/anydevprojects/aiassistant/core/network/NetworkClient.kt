package ru.anydevprojects.aiassistant.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "http://10.0.2.2:8080/"

internal fun getNetworkClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BASE_URL)
            }
        }
    }
}
