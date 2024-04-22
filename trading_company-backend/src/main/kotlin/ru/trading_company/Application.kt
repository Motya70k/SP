package ru.trading_company

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.trading_company.features.login.configureLoginRouting
import ru.trading_company.features.register.configureRegisterRouting
import ru.trading_company.plugins.*

fun main() {
    Database.connect("jdbc:mysql://localhost:3307/trading_company_db",
        driver = "com.mysql.cj.jdbc.Driver", user = "root", password = "")

    embeddedServer(CIO, port = 3000, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
}
