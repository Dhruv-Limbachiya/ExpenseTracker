package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.repositories.ExpenseRepository

class GetCurrentMonthTotalSpent(
    private var expenseRepository: ExpenseRepository
) {
    suspend fun invoke(){
        expenseRepository.getCurrentMonthExpenses()
    }
}