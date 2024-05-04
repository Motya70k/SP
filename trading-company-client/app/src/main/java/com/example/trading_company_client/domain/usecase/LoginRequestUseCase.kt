package com.example.trading_company_client.domain.usecase

import com.example.trading_company_client.data.model.requests.LoginRequest
import com.example.trading_company_client.data.model.response.BaseResponse
import com.example.trading_company_client.domain.repository.LoginRequestRepository

class LoginRequestUseCase (
    private val loginRequestRepository: LoginRequestRepository
){
    suspend fun employeeLogin(loginRequest: LoginRequest): BaseResponse {
        return loginRequestRepository.employeeLogin(loginRequest)
    }
}