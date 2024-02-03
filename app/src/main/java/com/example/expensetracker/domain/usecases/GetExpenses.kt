package com.example.expensetracker.domain.usecases

import android.util.Log
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenses constructor(
    private var expenseRepository: ExpenseRepository
) {

    suspend operator fun invoke(): Flow<List<Expense>> {
      return expenseRepository.getAllExpenses()
    }
}