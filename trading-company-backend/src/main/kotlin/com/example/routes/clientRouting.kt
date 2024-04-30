package com.example.routes

import com.example.domain.usecase.ClientUseCase
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.clientRouting(clientUseCase: ClientUseCase) {

    authenticate("jwt") {

    }
}