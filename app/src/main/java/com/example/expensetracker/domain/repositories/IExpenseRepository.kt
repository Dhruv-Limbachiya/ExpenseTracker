package com.example.expensetracker.domain.repositories

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named


class IExpenseRepository() : ExpenseRepository {

    @Inject
    @Named("expense_tracker_db")
    lateinit var expenseTrackerDB: ExpenseTrackerDB

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

    override suspend fun getCurrentMonthExpenses(): Flow<Double?> {
        return expenseTrackerDB.getExpenseDao().getExpensesOfCurrentMonth()
    }
}