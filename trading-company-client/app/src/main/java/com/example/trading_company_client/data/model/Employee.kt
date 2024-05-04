package com.example.trading_company_client.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val id: Int,
    val login: String,
    val password: String,
    val name: String,
    val lastname: String,
    val phoneNumber: String,
    val role: String,
    val isActive: Boolean = false
)
