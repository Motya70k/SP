package com.example.trading_company_client.domain.usecase

import com.example.trading_company_client.data.model.Item
import com.example.trading_company_client.data.model.requests.AddItemRequest
import com.example.trading_company_client.domain.repository.ItemRepository

class ItemUseCase (
    private val itemRepository: ItemRepository
) {
    suspend fun getAllItems(): List<Item> {
        return itemRepository.getAllItems()
    }

    suspend fun addItem(addItemRequest: AddItemRequest) {
        itemRepository.addItem(addItemRequest)
    }
}