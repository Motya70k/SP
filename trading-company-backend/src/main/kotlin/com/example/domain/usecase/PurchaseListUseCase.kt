package com.example.domain.usecase

import com.example.data.model.PurchaseListModel
import com.example.domain.repository.PurchaseListRepository

class PurchaseListUseCase(
    private val purchaseListRepository: PurchaseListRepository
) {
    suspend fun addPurchase(purchase: PurchaseListModel) {
        purchaseListRepository.addPurchase(purchase = purchase)
    }

    suspend fun getAllPurchase(): List<PurchaseListModel> {
        return purchaseListRepository.getAllPurchase()
    }

    suspend fun updatePurchase(purchase: PurchaseListModel, ownerItemId: Int) {
        purchaseListRepository.updatePurchase(purchase = purchase, ownerItemId = ownerItemId)
    }

    suspend fun deletePurchase(purchaseId: Int, ownerItemId: Int) {
        purchaseListRepository.deletePurchase(purchaseId = purchaseId, ownerItemId = ownerItemId)
    }
}