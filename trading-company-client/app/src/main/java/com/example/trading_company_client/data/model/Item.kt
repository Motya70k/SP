package com.example.trading_company_client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val quantity: Int,
    val addedBy: Int
)
