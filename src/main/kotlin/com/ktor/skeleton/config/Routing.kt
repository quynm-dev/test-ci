package com.ktor.skeleton.config

import com.ktor.skeleton.controller.bookController
import com.ktor.skeleton.controller.userController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            userController()
            bookController()
        }
    }
}