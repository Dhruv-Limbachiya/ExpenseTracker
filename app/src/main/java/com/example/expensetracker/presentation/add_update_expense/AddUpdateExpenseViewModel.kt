package com.example.expensetracker.presentation.add_update_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.usecases.UseCase
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.toExpenseEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddUpdateExpenseViewModel constructor(
    private val useCase: UseCase
) : ViewModel(){

    fun addExpense(expense: ExpenseData) = viewModelScope.async {
        val isExpenseInserted = useCase.addOrUpdateExpense(expense.toExpenseEntity())
        return@async isExpenseInserted
    }

}