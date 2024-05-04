package com.example.trading_company_client.domain.repository

import com.example.trading_company_client.data.model.requests.LoginRequest
import com.example.trading_company_client.data.model.response.BaseResponse

interface LoginRequestRepository {
    suspend fun employeeLogin(loginRequest: LoginRequest): BaseResponse
}