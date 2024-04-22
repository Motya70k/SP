package ru.trading_company

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.trading_company.plugins.*

fun main() {
    embeddedServer(CIO, port = 3000, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
