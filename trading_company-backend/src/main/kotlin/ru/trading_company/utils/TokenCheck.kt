package ru.trading_company.utils

import ru.trading_company.database.tokens.Tokens

object TokenCheck {

    fun isTokenValid(token: String): Boolean = Tokens.fetchTokens().firstOrNull { it.token == token } != null
}