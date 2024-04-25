package ru.trading_company.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    JwtConfig.initialize("trading-company")
    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asString()
                if (claim != null) {
                    UserLoginPrincipalForUser(claim)
                } else {
                    null
                }
            }
        }
    }
}