package com.example.plugins

import com.example.data.model.EmployeeModel
import com.example.data.model.RoleModel
import com.example.domain.usecase.EmployeeUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity(employeeUseCase: EmployeeUseCase) {
    authentication {
        jwt("jwt") {
            verifier(employeeUseCase.getJwtVerifier())
            realm = "Service Server"
            validate {
                val payload = it.payload
                val login = payload.getClaim("login").asString()
                val employee = employeeUseCase.findEmployeeByLogin(login = login)
                employee
            }
        }
    }
}
