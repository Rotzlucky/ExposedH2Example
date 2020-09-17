package com.talentsconnect

import com.example.module
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@KtorExperimentalAPI
class ApplicationTest : BaseTestConfig() {

    @Test
    fun `Assert that application runs`() {
        testApp {
            assertTrue { true }
        }
    }
}

@KtorExperimentalAPI
open class BaseTestConfig {

    val objectMapper = jacksonObjectMapper()

    fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            testConfig(this)
            module(testing = true)
        }) { callback() }
    }

    private fun testConfig(application: Application) {
        (application.environment.config as MapApplicationConfig).apply {
            put("app.database.jdbcUrl","jdbc:h2:mem:test")
            put("app.database.driver", "org.h2.Driver")
        }
    }
}