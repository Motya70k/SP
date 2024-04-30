package com.example.data.repository

import com.example.data.model.OrderModel
import com.example.data.model.table.ClientTable
import com.example.data.model.table.ItemTable
import com.example.data.model.table.OrderTable
import com.example.domain.repository.OrderRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OrderRepositoryImpl : OrderRepository {
    override suspend fun addOrder(order: OrderModel) {
        val selectedItemName = getItemName(order)
        val selectedClientId = getClientId(order)
        dbQuery {
            OrderTable.insert { table ->
                table[itemId] = order.itemId
                table[itemName] = selectedItemName!!
                table[clientId] = selectedClientId!!
                table[clientName] = order.clientName
                table[clientLastname] = order.clientLastname
            }
        }
    }

    override suspend fun getAllOrders(): List<OrderModel> {
        return dbQuery {
            OrderTable.selectAll()
                .mapNotNull { rowToOrder(it) }
        }
    }

    override suspend fun updateOrder(order: OrderModel) {
        val selectedItemName = getItemName(order)
        val selectedClientId = getClientId(order)
        dbQuery {
            OrderTable.update(
                where = {
                    OrderTable.id.eq(order.id)
                }
            ) { table ->
                table[itemId] = order.itemId
                table[itemName] = selectedItemName!!
                table[clientId] = selectedClientId!!
                table[clientName] = order.clientName
                table[clientLastname] = order.clientLastname
            }
        }
    }

    override suspend fun deleteOrder(id: Int) {
        dbQuery {
            OrderTable.deleteWhere { OrderTable.id.eq(id) }
        }
    }

    private fun rowToOrder(row: ResultRow): OrderModel? {
        if (row == null) {
            return null
        }

        return OrderModel(
            id = row[OrderTable.id],
            itemId = row[OrderTable.itemId],
            itemName = row[OrderTable.itemName],
            clientId = row[OrderTable.clientId],
            clientName = row[OrderTable.clientName],
            clientLastname = row[OrderTable.clientLastname]
        )
    }

    private suspend fun getItemName(order: OrderModel): String? {
        return dbQuery {
            ItemTable.selectAll().where { ItemTable.id eq order.itemId }
                .map { it[ItemTable.name] }
                .singleOrNull()
        }
    }

    private suspend fun getClientId(order: OrderModel): Int? {
        return dbQuery {
            ClientTable.selectAll().where {
                ClientTable.name eq order.clientName and
                        (ClientTable.lastname eq order.clientLastname)
            }
                .map { it[ClientTable.id] }
                .singleOrNull()
        }
    }
}