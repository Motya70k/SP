package ru.trading_company.features.login

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.trading_company.database.tokens.TokenDTO
import ru.trading_company.database.tokens.Tokens
import ru.trading_company.database.users.Users
import ru.trading_company.security.JwtConfig
import java.util.UUID

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginRemote>()
        val userDto = Users.fetchUser(receive.login)

        if (userDto == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            val checkPassword = BCrypt.verifyer().verify(receive.password.toCharArray(), userDto.password.toCharArray())
            if (checkPassword.verified) {
                val token = JwtConfig.instance.createAccessToken(receive.login)
                Tokens.insert(
                    TokenDTO(id = UUID.randomUUID().toString(), login = receive.login, token = token)
                )

                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}