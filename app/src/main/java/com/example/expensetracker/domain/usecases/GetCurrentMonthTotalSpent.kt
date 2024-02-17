package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentMonthTotalSpent(
    private var expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(): Flow<Double?> {
       return expenseRepository.getCurrentMonthExpenses()
    }
}