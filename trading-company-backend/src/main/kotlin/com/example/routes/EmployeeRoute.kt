package com.example.routes

import com.example.authentification.hash
import com.example.data.model.EmployeeModel
import com.example.data.model.getRoleByString
import com.example.data.model.requests.LoginRequest
import com.example.data.model.requests.RegisterRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.EmployeeUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.employeeRoute(employeeUseCase: EmployeeUseCase) {

    val hashPassword = { p: String -> hash(password = p) }

    post("/register") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val employee = EmployeeModel(
                id = 0,
                login = registerRequest.login,
                password = hashPassword(registerRequest.password),
                name = registerRequest.name,
                lastname = registerRequest.lastname,
                phoneNumber = registerRequest.phoneNumber,
                role = registerRequest.role.getRoleByString()
            )

            employeeUseCase.createEmployee(employee)
            call.respond(HttpStatusCode.OK, BaseResponse(true, employeeUseCase.generateToken(employeeModel = employee)))

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Conflict, BaseResponse(
                    false,
                    e.message ?: Constants.Error.GENERAL
                )
            )
        }
    }

    post("/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val employee = employeeUseCase.findEmployeeByLogin(loginRequest.login)

            if (employee == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.WRONG_LOGIN))
            } else {
                if (employee.password == hashPassword(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, BaseResponse(true, employeeUseCase.generateToken(employeeModel = employee)))
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest, BaseResponse(
                            false,
                            Constants.Error.INCORRECT_PASSWORD
                        )
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Conflict, BaseResponse(
                    false,
                    e.message ?: Constants.Error.GENERAL
                )
            )
        }
    }

    authenticate("jwt") {

        get("/get-employee-info") {
            try {
                val employee = call.principal<EmployeeModel>()

                if (employee != null) {
                    call.respond(HttpStatusCode.OK, employee)
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, Constants.Error.EMPLOYEE_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            }
        }
    }
}