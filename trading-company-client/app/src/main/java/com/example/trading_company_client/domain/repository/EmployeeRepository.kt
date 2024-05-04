package com.example.trading_company_client.domain.repository

import com.example.trading_company_client.data.model.Employee
import com.example.trading_company_client.data.model.requests.RegisterRequest

interface EmployeeRepository {

    suspend fun getAllEmployees(): List<Employee>

    suspend fun registerEmployee(registerRequest: RegisterRequest)
}