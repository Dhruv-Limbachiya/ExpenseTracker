package com.example.expensetracker.presentation.add_update_expense.data

import com.example.expensetracker.common.toDate
import com.example.expensetracker.data.db.entities.Expense

data class ExpenseData(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val amount: String = "",
    val categoryId: Int = 0,
    val date: String? = null
) {
    companion object {
        val INVALID_EXPENSE_DATA = ExpenseData()

        fun ExpenseData.toExpenseEntity(isUpdate: Boolean): Expense {
            return Expense(
                id = id,
                title = title,
                description = description,
                amount = if(amount.isEmpty()) 0.0 else amount.toDouble(),
                categoryId = categoryId,
                date = if(isUpdate) date else System.currentTimeMillis().toDate()
            )
        }
    }
}
