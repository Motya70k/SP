package com.example.trading_company_client.data.repository

import com.example.trading_company_client.data.model.Item
import com.example.trading_company_client.data.model.requests.AddItemRequest
import com.example.trading_company_client.data.model.response.BaseResponse
import com.example.trading_company_client.domain.repository.ItemRepository
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

class ItemRepositoryImpl : ItemRepository {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    override suspend fun getAllItems(): List<Item> {
        return try {
            val employeesResponse: List<Item> = client.get("http://192.168.1.111:3007/get-all-items").body()
            employeesResponse
        } catch (e: Exception) {
            BaseResponse(false, e.message ?: "Unknown message")
            emptyList()
        }
    }

    override suspend fun addItem(addItemRequest: AddItemRequest) {
        try {
            val response: HttpResponse = client.post("http://192.168.1.111:3007/create-item") {
                contentType(ContentType.Application.Json)
                setBody(addItemRequest)
            }
            val responseBody = response.bodyAsText()
        } catch (e: Exception) {
            BaseResponse(false, e.message ?: "Unknown message")
        }
    }

    override suspend fun updateItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(itemId: Int) {
        TODO("Not yet implemented")
    }
}