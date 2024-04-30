package com.example.domain.repository

import com.example.data.model.ClientModel

interface ClientRepository {

    suspend fun addClient(client: ClientModel)

    suspend fun updateClient(client: ClientModel)

    suspend fun deleteClient(id: Int)

    suspend fun getAllClients(): List<ClientModel>
}