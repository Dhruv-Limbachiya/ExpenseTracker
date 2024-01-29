package com.example.expensetracker.data.repositories

import com.example.expensetracker.data.db.dao.ExpenseDao
import com.example.expensetracker.data.db.entities.Expense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExpenseRepository {

    suspend fun insertExpense(expense: Expense): Boolean

    suspend fun deleteExpense(expense: Expense): Boolean

    suspend fun getAllExpenses(): Flow<List<Expense>>

    suspend fun getCurrentMonthExpenses(): Flow<Double?>
}