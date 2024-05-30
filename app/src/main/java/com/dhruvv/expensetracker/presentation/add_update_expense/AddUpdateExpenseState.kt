package com.dhruvv.expensetracker.presentation.add_update_expense

import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.INVALID_EXPENSE_DATA

data class AddUpdateExpenseState(
    var expenseData: ExpenseData = INVALID_EXPENSE_DATA,
    var isUpdate: Boolean = false,
) {
    companion object {
        val INVALID_ADD_UPDATE_EXPENSE_STATE = AddUpdateExpenseState()
    }
}
