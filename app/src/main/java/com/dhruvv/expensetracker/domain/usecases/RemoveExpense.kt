package com.dhruvv.expensetracker.domain.usecases

import com.dhruvv.expensetracker.data.db.entities.Expense
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository

class RemoveExpense(
    private var expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: Expense) {
        expenseRepository.deleteExpense(expense)
    }
}
