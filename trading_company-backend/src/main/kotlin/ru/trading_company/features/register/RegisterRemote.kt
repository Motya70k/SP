package ru.trading_company.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRemote(
    val login: String,
    val name: String,
    val surname: String,
    val password: String,
    val phone: String,
    val role: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)
