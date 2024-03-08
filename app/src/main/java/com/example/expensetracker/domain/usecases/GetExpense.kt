package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpense constructor(
    private var expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(expenseId: Int): Expense? {
        return expenseRepository.getExpenseByID(expenseId = expenseId)
    }
}