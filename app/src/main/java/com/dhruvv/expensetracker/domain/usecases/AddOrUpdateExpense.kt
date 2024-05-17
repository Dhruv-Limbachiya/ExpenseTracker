package com.dhruvv.expensetracker.domain.usecases

import com.dhruvv.expensetracker.data.db.entities.Expense
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository

class AddOrUpdateExpense(
    private var expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: Expense): Boolean {
        return expenseRepository.insertExpense(expense)
    }
}
