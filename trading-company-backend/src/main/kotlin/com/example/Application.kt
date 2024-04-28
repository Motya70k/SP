package com.example

import com.example.authentification.JwtService
import com.example.data.repository.EmployeeRepositoryImpl
import com.example.data.repository.ItemRepositoryImpl
import com.example.domain.usecase.EmployeeUseCase
import com.example.domain.usecase.ItemUseCase
import com.example.plugins.*
import com.example.plugins.DatabaseFactory.initializeDataBase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 3007, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    val jwtService = JwtService()
    val employeeRepository = EmployeeRepositoryImpl()
    val itemRepository = ItemRepositoryImpl()
    val employeeUseCase = EmployeeUseCase(employeeRepository, jwtService)
    val itemUseCase = ItemUseCase(itemRepository)

    initializeDataBase()
    configureSerialization()
    configureMonitoring()
    configureSecurity(employeeUseCase)
//    configureRouting()
}
