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

const val BASE_URL = "http://192.168.31.32:8080/"

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        defaultRequest {
            url("http://192.168.31.32:8080/")
            contentType(ContentType.Application.Json)
        }
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
    }
}

internal fun getNetworkClient(): HttpClient {
    return HttpClient(CIO).config {
        install(ContentNegotiation) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "ktor.io"
                    parameters.append("token", "abc123")
                }
                contentType(ContentType.Application.Json)
                header("X-Custom-Header", "Hello")
            }
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
        }
    }
}
