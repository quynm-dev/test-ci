val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val microLoggingVersion: String by project
val exposedVersion: String by project
val koinVersion: String by project
val koinKspVersion: String by project
val hikariCPVersion: String by project
val resultVersion: String by project
val h2Version: String by project
val mySqlConnectorVersion: String by project
val konformVersion: String by project
val cohortVersion: String by project
val jUnitVersion: String by project
val mockkVersion: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.10"
    id("io.ktor.plugin") version "2.3.9"
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

group = "com.ktor.skeleton"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // region ktor
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")
    implementation("io.ktor:ktor-server-openapi:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-apache-jvm:$ktorVersion")
    // end region

    // region di
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    implementation("io.insert-koin:koin-annotations:$koinKspVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinKspVersion")
    // end region

    // region orm and db
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation("mysql:mysql-connector-java:$mySqlConnectorVersion")
    // end region

    // region validation and exception
    implementation("io.konform:konform-jvm:$konformVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    // end region

    // region logger
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$microLoggingVersion")
    // end region

    // region railway
    implementation("com.michael-bull.kotlin-result:kotlin-result:$resultVersion")
    // end region

    // region http client
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    // end region

    // region health check
    implementation("com.sksamuel.cohort:cohort-core:$cohortVersion")
    implementation("com.sksamuel.cohort:cohort-hikari:$cohortVersion")
    // end region

    // region security
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:+")
    // end region

    // region test
    testImplementation("com.h2database:h2:$h2Version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    // end region
}
