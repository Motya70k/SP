package ru.trading_company.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.trading_company.database.tokens.TokenDTO
import ru.trading_company.database.tokens.Tokens
import ru.trading_company.database.users.UserDTO
import ru.trading_company.database.users.Users
import ru.trading_company.security.JwtConfig
import java.util.UUID

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerRemote = call.receive<RegisterRemote>()
        val userDto = Users.fetchUser(registerRemote.login)

        if (userDto != null) {
            call.respond(HttpStatusCode.Conflict, "User already exist")
        } else {
            val token = JwtConfig.instance.createAccessToken(registerRemote.login)

            try {
                Users.insert(
                    UserDTO(
                        login = registerRemote.login,
                        name = registerRemote.name,
                        surname = registerRemote.surname,
                        password = registerRemote.password,
                        phone = registerRemote.phone,
                        role = registerRemote.role
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exist")
            }


            Tokens.insert(
                TokenDTO(id = UUID.randomUUID().toString(), login = registerRemote.login, token = token)
            )

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}