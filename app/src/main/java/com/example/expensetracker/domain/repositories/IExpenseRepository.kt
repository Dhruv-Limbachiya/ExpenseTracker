package com.example.expensetracker.domain.repositories

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.exp


class IExpenseRepository(private val expenseTrackerDB: ExpenseTrackerDB) : ExpenseRepository {

    override suspend fun insertExpense(expense: Expense): Boolean {
        val rowId = expenseTrackerDB.getExpenseDao().insertExpense(expense)
        return rowId != 0L
    }

    override suspend fun deleteExpense(expense: Expense): Boolean {
        val rowId = expenseTrackerDB.getExpenseDao().deleteExpense(expense)
        return rowId > 0
    }

    override suspend fun getAllExpenses(): Flow<List<Expense>> {
        return expenseTrackerDB.getExpenseDao().getAllExpenses()
    }

    override suspend fun getExpenseByID(expenseId: Int): Expense? {
        return expenseTrackerDB.getExpenseDao().getExpenseById(expenseId)
    }

    override suspend fun getCurrentMonthExpenses(): Flow<Double?> {
        return expenseTrackerDB.getExpenseDao().getExpensesOfCurrentMonth()
    }
}