package com.example.domain.repository

import com.example.data.model.ItemModel
import com.example.data.model.PurchaseListModel

interface PurchaseListRepository {

    suspend fun addPurchase(purchase: PurchaseListModel)

    suspend fun getAllPurchase(): List<PurchaseListModel>

    suspend fun updatePurchase(purchase: PurchaseListModel, ownerItemId: Int)

    suspend fun deletePurchase(purchaseId: Int, ownerItemId: Int)
}