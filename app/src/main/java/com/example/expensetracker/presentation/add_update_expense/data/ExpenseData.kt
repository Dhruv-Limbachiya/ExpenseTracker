package com.example.expensetracker.presentation.add_update_expense.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.expensetracker.data.db.entities.Expense

data class ExpenseData(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val amount: Double = 0.0,
    val categoryId: Int = 0,
    val date: String? = null
) {
    companion object {
        val INVALID_EXPENSE_DATA = ExpenseData()

        fun ExpenseData.toExpenseEntity() : Expense {
            return Expense(
                id = id,
                title = title,
                description = description,
                amount = amount,
                categoryId = categoryId,
                date = date
            )
        }
    }
}
