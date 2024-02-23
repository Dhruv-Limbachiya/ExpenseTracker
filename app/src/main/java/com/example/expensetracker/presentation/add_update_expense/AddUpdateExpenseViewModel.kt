package com.example.expensetracker.presentation.add_update_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.usecases.UseCase
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.toExpenseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddUpdateExpenseViewModel @Inject constructor (
    @Named("main_use_case") private val useCase: UseCase
) : ViewModel(){

    fun addExpense(expense: ExpenseData) = viewModelScope.async {
        val isExpenseInserted = useCase.addOrUpdateExpense(expense.toExpenseEntity())
        return@async isExpenseInserted
    }

}