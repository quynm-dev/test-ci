package com.ktor.skeleton.config

import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoinDI() {
    install(Koin) {
        slf4jLogger()
        defaultModule()
    }
}