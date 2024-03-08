package com.example.expensetracker.domain.repositories

import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeExpenseRepository : ExpenseRepository {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    private val _currentMonthExpense = MutableStateFlow(0.0)
    val currentMonthExpense: StateFlow<Double> = _currentMonthExpense

    override suspend fun insertExpense(expense: Expense): Boolean {
        val mutableExpenses = _expenses.value.toMutableList()
        val existedExpense = _expenses.value.firstOrNull { it.id == expense.id}

        if(existedExpense != null) {
            val index = _expenses.value.indexOf(existedExpense)
            mutableExpenses.removeAt(index)
            mutableExpenses.add(index,expense)
        } else {
            mutableExpenses.add(expense)
        }

        _expenses.value = mutableExpenses
        return true
    }

    override suspend fun deleteExpense(expense: Expense): Boolean {
        val mutableExpenses = _expenses.value.toMutableList()
        val isRemoved = mutableExpenses.remove(expense)
        _expenses.value = mutableExpenses
        return isRemoved
    }

    override suspend fun getAllExpenses(): Flow<List<Expense>> {
        return expenses
    }

    override suspend fun getExpenseByID(expenseId: Int): Expense? {
        return _expenses.value.find { it.id == expenseId }
    }

    override suspend fun getCurrentMonthExpenses(): Flow<Double?> {
        _currentMonthExpense.value = calculateCurrentMonthExpenses()
        return _currentMonthExpense
    }


    private fun calculateCurrentMonthExpenses(): Double {
        val currentMonth = expenses.value.filter {
            it.date!!.startsWith("2024-02")
        }

        val currentMonthExpense = currentMonth.sumOf {
            it.amount
        }

        return currentMonthExpense
    }

}