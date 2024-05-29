package ru.anydevprojects.aiassistant.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.anydevprojects.aiassistant.core.network.NetworkConstants.HTTP_HEADER_AUTHORIZATION

private const val BASE_URL = "http://192.168.31.32:8080/"

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
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
            header(HTTP_HEADER_AUTHORIZATION, "Bearer ANY_TOKEN")
        }
    }
}
