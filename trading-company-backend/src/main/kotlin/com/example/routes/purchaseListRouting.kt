package com.example.routes

import com.example.data.model.EmployeeModel
import com.example.data.model.ItemModel
import com.example.data.model.PurchaseListModel
import com.example.data.model.requests.AddItemRequest
import com.example.data.model.requests.AddPurchaseRequest
import com.example.data.model.requests.UpdatePurchaseRequest
import com.example.data.model.response.BaseResponse
import com.example.domain.usecase.ItemUseCase
import com.example.domain.usecase.PurchaseListUseCase
import com.example.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.purchaseListRouting(purchaseListUseCase: PurchaseListUseCase, itemUseCase: ItemUseCase) {

    authenticate("jwt") {

        get("/get-all-purchases") {
            try {
                val purchase = purchaseListUseCase.getAllPurchase()
                call.respond(HttpStatusCode.OK, purchase)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict, BaseResponse(
                        false,
                        e.message ?: Constants.Error.GENERAL
                    )
                )
            }
        }

        post("/create-purchase") {
            val purchaseRequest = call.receiveNullable<AddPurchaseRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }
            try {
                val itemExists = itemUseCase.checkItemExist(purchaseRequest.itemId)
                if (!itemExists) {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.ITEMID_DOESNT_EXIST))
                    return@post
                }

                val purchase = PurchaseListModel(
                    id = 0,
                    itemId = purchaseRequest.itemId,
                    name = "",
                    amount = purchaseRequest.amount,
                    cost = purchaseRequest.cost
                )

                purchaseListUseCase.addPurchase(purchase = purchase)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.PURCHASE_ADDED_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict, e.message ?: BaseResponse(
                        false,
                        Constants.Error.GENERAL
                    )
                )
            }
        }

        post("/update-purchase") {
            val purchaseRequest = call.receiveNullable<AddPurchaseRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val itemExists = itemUseCase.checkItemExist(purchaseRequest.itemId)
                if (!itemExists) {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.ITEMID_DOESNT_EXIST))
                    return@post
                }

                val purchase = PurchaseListModel(
                    id = purchaseRequest.id ?: 0,
                    itemId = purchaseRequest.itemId,
                    name = "",
                    amount = purchaseRequest.amount,
                    cost = purchaseRequest.cost
                )

                purchaseListUseCase.updatePurchase(purchase = purchase)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.PURCHASE_UPDATE_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict, e.message ?: BaseResponse(
                        false,
                        Constants.Error.GENERAL
                    )
                )
            }
        }

        delete("/delete-purchase") {
            val purchaseRequest = call.request.queryParameters[Constants.Value.ID]?.toInt() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@delete
            }

            try {
                purchaseListUseCase.deletePurchase(purchaseId = purchaseRequest)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.PURCHASE_DELETE_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict, e.message ?: BaseResponse(
                        false,
                        Constants.Error.GENERAL
                    )
                )
            }
        }
    }
}