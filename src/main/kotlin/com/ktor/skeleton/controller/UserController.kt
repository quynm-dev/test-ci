package com.ktor.skeleton.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userController() {
    route("/users") {
        get {
            call.respond(HttpStatusCode.OK, "hello world")
        }
    }
}