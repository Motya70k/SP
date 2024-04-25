package ru.trading_company.features.delete

import kotlinx.serialization.Serializable

@Serializable
data class UserDeleteRemote(
    val login: String
)