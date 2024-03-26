package com.ktor.skeleton

import com.ktor.skeleton.config.configureDatabase
import com.ktor.skeleton.config.configureKoinDI
import com.ktor.skeleton.config.configureRouting
import com.ktor.skeleton.config.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureDatabase()
    configureRouting()
    configureKoinDI()
    configureSerialization()
}
