package com.example.data.repository

import com.example.data.model.EmployeeModel
import com.example.data.model.getRoleByString
import com.example.data.model.getStringByRole
import com.example.data.model.table.EmployeeTable
import com.example.domain.repository.EmployeeRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class EmployeeRepositoryImpl : EmployeeRepository {

    override suspend fun getEmployeeByLogin(login: String): EmployeeModel? {
        return dbQuery {
            EmployeeTable.selectAll().where { EmployeeTable.login eq login }
                .map { rowToEmployee(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun insertEmployee(employee: EmployeeModel) {
        return dbQuery {
            EmployeeTable.insert { table ->
                table[login] = employee.login
                table[password] = employee.password
                table[name] = employee.name
                table[lastname] = employee.lastname
                table[phoneNumber] = employee.phoneNumber
                table[role] = employee.role.getStringByRole()
                table[isActive] = employee.isActive
            }
        }
    }

    override suspend fun getAllEmployees(): List<EmployeeModel> {
        return dbQuery {
            EmployeeTable.selectAll()
                .mapNotNull { rowToEmployee(row = it) }
        }
    }

    private fun rowToEmployee(row: ResultRow?): EmployeeModel? {
        if (row == null) {
            return null
        }
        return EmployeeModel(
            id = row[EmployeeTable.id],
            login = row[EmployeeTable.login],
            password = row[EmployeeTable.password],
            name = row[EmployeeTable.name],
            lastname = row[EmployeeTable.lastname],
            phoneNumber = row[EmployeeTable.phoneNumber],
            role = row[EmployeeTable.role].getRoleByString(),
            isActive = row[EmployeeTable.isActive]
        )
    }
}