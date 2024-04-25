package ru.trading_company.features.delete

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.trading_company.database.users.Users

class DeleteConroller(private val call: ApplicationCall) {

    suspend fun deleteUser() {
        val userDeleteRemote = call.receive<UserDeleteRemote>()
        val userDto = Users.fetchUser(userDeleteRemote.login)

        if (userDto == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            try {
                Users.deleteUser(
                    login = userDeleteRemote.login
                )
                call.respond(HttpStatusCode.OK, "User deleted")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, "User not exist")
            }
        }
    }
}