package com.ktor.skeleton

import com.ktor.skeleton.config.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureHealthCheck(configureDatabase())
    configureRouting()
    configureKoinDI()
    configureSerialization()
    configureExceptionInterceptor()
    configureSecurity()
}
