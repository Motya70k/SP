package com.example.domain.repository

import com.example.data.model.EmployeeModel

interface EmployeeRepository {

    suspend fun getEmployeeByLogin(login: String): EmployeeModel?

    suspend fun insertEmployee(employeeModel: EmployeeModel)

    suspend fun getAllEmployees(): List<EmployeeModel>
}