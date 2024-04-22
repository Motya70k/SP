package ru.trading_company.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.trading_company.database.tokens.TokenDTO
import ru.trading_company.database.tokens.Tokens
import ru.trading_company.database.users.Users
import java.util.UUID

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginRemote>()
        val userDto = Users.fetchUser(receive.login)

        if (userDto == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDto.password == receive.password) {
                val token = UUID.randomUUID().toString()
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