package com.dhruvv.expensetracker.domain.usecases

import com.dhruvv.expensetracker.data.db.entities.Expense
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository

class GetExpense constructor(
    private var expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: Int): Expense? {
        return expenseRepository.getExpenseByID(expenseId = expenseId)
    }
}