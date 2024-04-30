package com.example.domain.usecase

import com.example.data.model.ClientModel
import com.example.domain.repository.ClientRepository

class ClientUseCase (
    private val clientRepository: ClientRepository
) {

    suspend fun addClient(client: ClientModel) {
        clientRepository.addClient(client = client)
    }

    suspend fun getAllClients(): List<ClientModel> {
        return clientRepository.getAllClients()
    }

    suspend fun updateClient(client: ClientModel) {
        clientRepository.updateClient(client = client)
    }

    suspend fun deleteClient(id: Int) {
        clientRepository.deleteClient(id = id)
    }
}