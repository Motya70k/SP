package ru.trading_company.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JwtConfig private constructor(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer()
        .withAudience()
        .build()

    fun createAccessToken(login: String): String = JWT
        .create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(CLAIM, login)
        .sign(algorithm)

    companion object {
        private const val ISSUER = "trading-company"
        private const val AUDIENCE = "trading-company"
        const val CLAIM = "login"

        lateinit var instance: JwtConfig
            private set

        fun initialize(secret: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig(secret)
                }
            }
        }
    }
}