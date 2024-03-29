package com.ktor.skeleton.config

import com.ktor.skeleton.data.dto.error.response.ErrorResponseDto
import com.ktor.skeleton.data.dto.session.UserSessionDto
import com.ktor.skeleton.error.ErrorCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.withRoles(vararg roles: String, build: Route.() -> Unit) {
    val route = createChild(object: RouteSelector() {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation {
            return RouteSelectorEvaluation.Transparent
        }
    })

    route.install(RoleAuthorizationPlugin) {
        roles(roles.toSet())
    }

    route.build()
}

class RoleBaseConfiguration {
    val requiredRoles = mutableSetOf<String>()
    fun roles(roles: Set<String>) {
        requiredRoles.addAll(roles)
    }
}

val RoleAuthorizationPlugin = createRouteScopedPlugin("RoleAuthorizationPlugin", ::RoleBaseConfiguration) {
    on(AuthenticationChecked) { call ->
        val userSession = call.sessions.get<UserSessionDto>()
        val forbiddenError = ErrorResponseDto(ErrorCode.UserError.Forbidden.number, "Forbidden")
        if (userSession == null) {
            call.respond(HttpStatusCode.Forbidden, forbiddenError)
        }

        val role = userSession?.role
        if (pluginConfig.requiredRoles.isNotEmpty() && role !in pluginConfig.requiredRoles) {
            call.respond(HttpStatusCode.Forbidden, forbiddenError)
        }
    }
}