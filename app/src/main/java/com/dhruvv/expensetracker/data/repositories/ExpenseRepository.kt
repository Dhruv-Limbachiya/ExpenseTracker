package com.dhruvv.expensetracker.data.repositories

import com.dhruvv.expensetracker.data.db.entities.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun insertExpense(expense: Expense): Boolean

    suspend fun deleteExpense(expense: Expense): Boolean

    suspend fun getAllExpenses(): Flow<List<Expense>>

    suspend fun getExpenseByID(expenseId: Int) : Expense?

    suspend fun getCurrentMonthExpenses(): Flow<Double?>
}