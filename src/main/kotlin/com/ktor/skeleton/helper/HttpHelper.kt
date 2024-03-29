package com.ktor.skeleton.helper

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

class HttpHelper {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        install(Logging) { level = LogLevel.INFO }
    }

    suspend fun get(url: String, token: String?): HttpResponse {
        try {
            return runBlocking {
                httpClient.get(url) {
                    headers {
                        if (!token.isNullOrEmpty()) {
                            append(HttpHeaders.Authorization, token)
                        }
                    }
                }
            }
        } catch (err: ClientRequestException) {
            logger.error { err.message }
            throw err
        } catch (err: ServerResponseException) {
            logger.error { err.message }
            throw err
        }
    }

    suspend fun post(url: String, requestBody: Parameters, contentType: ContentType, token: String?): HttpResponse {
        try {
            return runBlocking {
                httpClient.post(url) {
                    when(contentType) {
                        ContentType.Application.Json -> setBody(requestBody)
                        ContentType.MultiPart.FormData -> setBody(FormDataContent(requestBody))
                    }
                    contentType(contentType)
                    headers {
                        if (!token.isNullOrEmpty()) {
                            append(HttpHeaders.Authorization, token)
                        }
                    }
                }
            }
        } catch (err: ClientRequestException) {
            logger.error { err.message }
            throw err
        } catch (err: ServerResponseException) {
            logger.error { err.message }
            throw err
        }
    }
}