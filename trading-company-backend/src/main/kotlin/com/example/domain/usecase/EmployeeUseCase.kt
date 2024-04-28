package com.example.domain.usecase

import com.auth0.jwt.JWTVerifier
import com.example.authentification.JwtService
import com.example.data.model.EmployeeModel
import com.example.domain.repository.EmployeeRepository

class EmployeeUseCase(
    private val repositoryImpl: EmployeeRepository,
    private val jwtService: JwtService
) {

    suspend fun createEmployee(employeeModel: EmployeeModel) = repositoryImpl.insertEmployee(employeeModel = employeeModel)

    suspend fun findEmployeeByLogin(login: String) = repositoryImpl.getEmployeeByLogin(login = login)

    fun generateToken(employeeModel: EmployeeModel): String = jwtService.generateToken(employee = employeeModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()

}