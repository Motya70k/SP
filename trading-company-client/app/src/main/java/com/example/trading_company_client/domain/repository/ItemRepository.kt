package com.example.trading_company_client.domain.repository

import com.example.trading_company_client.data.model.Item
import com.example.trading_company_client.data.model.requests.AddItemRequest

interface ItemRepository {
    suspend fun getAllItems(): List<Item>

    suspend fun addItem(addItemRequest: AddItemRequest)

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(itemId: Int,)
}