package com.example

import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    environmentConfig = environment.config
    initDatabase()
}

@KtorExperimentalAPI
lateinit var environmentConfig: ApplicationConfig

val serverLogger: Logger = LoggerFactory.getLogger("test-service")

@KtorExperimentalAPI
fun initDatabase() {
    val jdbcUrl = environmentConfig.propertyOrNull("app.database.jdbcUrl")?.getString() ?: ""
    val driver = environmentConfig.propertyOrNull("app.database.driver")?.getString() ?: ""
    val username = environmentConfig.propertyOrNull("app.database.username")?.getString() ?: ""
    val password = environmentConfig.propertyOrNull("app.database.password")?.getString() ?: ""

    serverLogger.info("Connecting to $jdbcUrl with driver $driver")

    if (jdbcUrl.isEmpty()) {
        throw IllegalStateException("No 'app.database.jdbcUrl' found! Exiting.")
    }

    Database.connect(
        jdbcUrl,
        driver = driver,
        user = username,
        password = password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Users
        )
    }
}