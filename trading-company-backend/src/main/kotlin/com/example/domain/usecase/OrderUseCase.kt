package com.example.domain.usecase

import com.example.data.model.OrderModel
import com.example.domain.repository.OrderRepository

class OrderUseCase(
    private val orderRepository: OrderRepository
) {

    suspend fun addOrder(order: OrderModel) {
        orderRepository.addOrder(order = order)
    }

    suspend fun getAllOrders(): List<OrderModel> {
        return orderRepository.getAllOrders()
    }

    suspend fun updateOrder(order: OrderModel) {
        orderRepository.updateOrder(order = order)
    }

    suspend fun deleteOrder(id: Int) {
        orderRepository.deleteOrder(id = id)
    }
}