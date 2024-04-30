package com.example.routes

import com.example.domain.usecase.OrderUseCase
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.orderRouting(orderUseCase: OrderUseCase) {

    authenticate("jwt") {

    }
}