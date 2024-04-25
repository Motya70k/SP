package ru.trading_company.security

import io.ktor.server.auth.*

data class UserLoginPrincipalForUser(val login: String) : Principal
