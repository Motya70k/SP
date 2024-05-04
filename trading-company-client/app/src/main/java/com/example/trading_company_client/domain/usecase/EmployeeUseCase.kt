package com.example.trading_company_client.domain.usecase

import com.example.trading_company_client.data.model.Employee
import com.example.trading_company_client.data.model.requests.RegisterRequest
import com.example.trading_company_client.domain.repository.EmployeeRepository

class EmployeeUseCase (
    private val employeeRepository: EmployeeRepository
) {
    suspend fun getAllEmployees(): List<Employee> {
        return employeeRepository.getAllEmployees()
    }

    suspend fun registerEmployee(registerRequest: RegisterRequest) {
        employeeRepository.registerEmployee(registerRequest)
    }
}