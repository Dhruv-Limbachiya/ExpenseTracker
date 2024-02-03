package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository

class AddOrUpdateExpense(
    private var expenseRepository: ExpenseRepository
) {

    suspend fun invoke(expense: Expense) {
        expenseRepository.insertExpense(expense)
    }
}