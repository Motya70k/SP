package ru.trading_company.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val login = Users.varchar("login", 50)
    private val name = Users.varchar("name", 50)
    private val surname = Users.varchar("surname", 50)
    private val password = Users.varchar("password", 255)
    private val phone = Users.varchar("phone", 20)
    private val role = Users.varchar("role", 50)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[name] = userDTO.name
                it[surname] = userDTO.surname
                it[password] = userDTO.password
                it[phone] = userDTO.phone
                it[role] = userDTO.role
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.selectAll().where { Users.login.eq(login) }.single()
                UserDTO(
                    login = userModel[Users.login],
                    name = userModel[name],
                    surname = userModel[surname],
                    password = userModel[password],
                    phone = userModel[phone],
                    role = userModel[role]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}