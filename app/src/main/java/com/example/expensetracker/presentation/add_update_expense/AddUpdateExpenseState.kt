package com.example.expensetracker.presentation.add_update_expense

import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.INVALID_EXPENSE_DATA

data class AddUpdateExpenseState(
    val expenseData: ExpenseData = INVALID_EXPENSE_DATA,
    val isUpdate: Boolean = false
) {
    companion object {
        val INVALID_ADD_UPDATE_EXPENSE_STATE = AddUpdateExpenseState()
    }
}