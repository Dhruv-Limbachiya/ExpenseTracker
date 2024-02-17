package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository

class AddOrUpdateExpense(
    private var expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense): Boolean {
        return expenseRepository.insertExpense(expense)
    }
}