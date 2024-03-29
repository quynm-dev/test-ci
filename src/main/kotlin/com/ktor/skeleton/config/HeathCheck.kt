package com.ktor.skeleton.config

import com.sksamuel.cohort.Cohort
import com.sksamuel.cohort.HealthCheckRegistry
import com.sksamuel.cohort.cpu.ProcessCpuHealthCheck
import com.sksamuel.cohort.hikari.HikariConnectionsHealthCheck
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlin.time.Duration.Companion.seconds

fun Application.configureHealthCheck(dataSource: HikariDataSource) {
    install(Cohort) {
        val healthCheckRegistry = HealthCheckRegistry(Dispatchers.Default) {
            val dbCheck = HikariConnectionsHealthCheck(dataSource, 1)
            register(dbCheck.name, dbCheck, 5.seconds, 5.seconds)

            val cpuHealthCheck = ProcessCpuHealthCheck(0.8)
            register(cpuHealthCheck.name, cpuHealthCheck, 5.seconds, 5.seconds)
        }

        healthcheck("/healthz", healthCheckRegistry)
    }
}