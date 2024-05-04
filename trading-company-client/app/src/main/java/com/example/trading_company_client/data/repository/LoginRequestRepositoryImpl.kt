package com.example.trading_company_client.data.repository

import com.example.trading_company_client.data.model.requests.LoginRequest
import com.example.trading_company_client.data.model.response.BaseResponse
import com.example.trading_company_client.domain.repository.LoginRequestRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class LoginRequestRepositoryImpl : LoginRequestRepository {

    override suspend fun employeeLogin(loginRequest: LoginRequest): BaseResponse {
        return try {
            val client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val response: HttpResponse = client.post("http://192.168.1.111:3007/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }

            val responseBody = response.bodyAsText()
            val baseResponse = Json.decodeFromString(BaseResponse.serializer(), responseBody)
            baseResponse
        } catch (e: Exception) {
            BaseResponse(false, e.message ?: "Unknown message")
        }
    }
}