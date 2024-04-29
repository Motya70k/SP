package com.example.plugins

import com.example.domain.usecase.EmployeeUseCase
import com.example.domain.usecase.ItemUseCase
import com.example.domain.usecase.PurchaseListUseCase
import com.example.routes.employeeRoute
import com.example.routes.itemRouting
import com.example.routes.purchaseListRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(employeeUseCase: EmployeeUseCase,
                                 itemUseCase: ItemUseCase,
                                 purchaseListUseCase: PurchaseListUseCase) {

    routing {
        employeeRoute(employeeUseCase = employeeUseCase)
        itemRouting(itemUseCase = itemUseCase)
        purchaseListRouting(purchaseListUseCase = purchaseListUseCase)
    }
}
