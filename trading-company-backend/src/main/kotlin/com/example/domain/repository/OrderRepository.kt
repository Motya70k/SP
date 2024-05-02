package com.example.domain.repository

import com.example.data.model.ClientModel
import com.example.data.model.OrderModel

interface OrderRepository {

    suspend fun addOrder(order: OrderModel)

    suspend fun getAllOrders(): List<OrderModel>

    suspend fun updateOrder(order: OrderModel)

    suspend fun deleteOrder(id: Int)
}