package ru.trading_company.features.delete

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserDeleteRouting() {
    routing {
        delete("/users/delete") {
            val deleteContoller = DeleteConroller(call)
            deleteContoller.deleteUser()
        }
    }
}