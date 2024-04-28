package com.example.data.model

import io.ktor.server.auth.*

data class EmployeeModel(
    val id: Int,
    val login: String,
    val password: String,
    val name: String,
    val lastname: String,
    val phoneNumber: String,
    val role: RoleModel,
    val isActive: Boolean = false,
): Principal
