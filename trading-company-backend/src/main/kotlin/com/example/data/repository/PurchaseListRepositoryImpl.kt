package com.example.data.repository

import com.example.data.model.PurchaseListModel

import com.example.data.model.table.PurchaseListTable
import com.example.domain.repository.PurchaseListRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PurchaseListRepositoryImpl : PurchaseListRepository {
    override suspend fun addPurchase(purchase: PurchaseListModel) {
        dbQuery {
            PurchaseListTable.insert { table ->
                table[itemId] = purchase.itemId
                table[amount] = purchase.amount
                table[cost] = purchase.cost
            }
        }
    }

    override suspend fun getAllPurchase(): List<PurchaseListModel> {
        return dbQuery {
            PurchaseListTable.selectAll()
                .mapNotNull { rowToPurchase(it) }
        }
    }

    override suspend fun updatePurchase(purchase: PurchaseListModel, ownerItemId: Int) {
        dbQuery {
            PurchaseListTable.update(
                where = {
                    PurchaseListTable.itemId.eq(ownerItemId) and (PurchaseListTable.id.eq(purchase.id))
                }
            ) { table ->
                table[itemId] = ownerItemId
                table[amount] = purchase.amount
                table[cost] = purchase.cost
            }
        }
    }

    override suspend fun deletePurchase(purchaseId: Int, ownerItemId: Int) {
        dbQuery {
            PurchaseListTable.deleteWhere { id.eq(purchaseId) and itemId.eq(ownerItemId) }
        }
    }

    private fun rowToPurchase(row: ResultRow): PurchaseListModel? {
        if (row == null) {
            return null
        }

        return PurchaseListModel(
            id = row[PurchaseListTable.id],
            itemId = row[PurchaseListTable.itemId],
            amount = row[PurchaseListTable.amount],
            cost = row[PurchaseListTable.cost]
        )
    }
}