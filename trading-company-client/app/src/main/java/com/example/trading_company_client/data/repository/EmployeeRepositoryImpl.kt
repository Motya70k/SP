package com.example.trading_company_client.data.repository

import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.trading_company_client.data.model.Employee
import com.example.trading_company_client.data.model.requests.RegisterRequest
import com.example.trading_company_client.data.model.response.BaseResponse
import com.example.trading_company_client.domain.repository.EmployeeRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class EmployeeRepositoryImpl : EmployeeRepository {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    @OptIn(UnstableApi::class)
    override suspend fun getAllEmployees(): List<Employee> {
        return try {
            val employeesResponse: List<Employee> = client.get("http://192.168.1.111:3007/get-all-employees").body()
            employeesResponse
        } catch (e: Exception) {
            Log.e("EmployeeRepository", "Error fetching employees", e)
            BaseResponse(false, e.message ?: "Unknown message")
            emptyList()
        }
    }

    override suspend fun registerEmployee(registerRequest: RegisterRequest) {
        try {
            val response: HttpResponse = client.post("http://192.168.1.111:3007/register") {
                contentType(ContentType.Application.Json)
                setBody(registerRequest)
            }
            val responseBody = response.bodyAsText()
        } catch (e: Exception) {
            BaseResponse(false, e.message ?: "Unknown message")
        }
    }
}