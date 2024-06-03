package com.dhruvv.expensetracker.domain.usecases

import com.dhruvv.expensetracker.data.db.entities.Expense
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpenses constructor(
    private var expenseRepository: ExpenseRepository,
) {
    suspend operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}
