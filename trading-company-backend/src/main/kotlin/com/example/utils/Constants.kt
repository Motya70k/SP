package com.example.utils

class Constants {

    object Role {
        const val DIRECTOR = "director"
        const val EMPLOYEE = "employee"
    }

    object Error {
        const val GENERAL = "Something went wrong"
        const val WRONG_LOGIN = "Wrong login"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val MISSING_FIELDS = "Missing some fields"
        const val EMPLOYEE_NOT_FOUND = "Employee not found"
    }

    object Success {
        const val ITEM_ADDED_SUCCESSFULLY = "Item added successfully"
        const val ITEM_UPDATE_SUCCESSFULLY = "Item updated successfully"
        const val ITEM_DELETE_SUCCESSFULLY = "Item deleted successfully"

        const val PURCHASE_ADDED_SUCCESSFULLY = "Purchase added successfully"
        const val PURCHASE_UPDATE_SUCCESSFULLY = "Purchase updated successfully"
        const val PURCHASE_DELETE_SUCCESSFULLY = "Purchase deleted successfully"
    }

    object Value {
        const val ID = "id"
    }
}