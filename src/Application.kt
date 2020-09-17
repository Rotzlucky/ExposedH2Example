package com.example

import io.ktor.application.Application
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    initDatabase()
}

@KtorExperimentalAPI
fun initDatabase() {
    Database.connect(
        "jdbc:h2:mem:ktorm;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver"
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Users
        )
    }
}