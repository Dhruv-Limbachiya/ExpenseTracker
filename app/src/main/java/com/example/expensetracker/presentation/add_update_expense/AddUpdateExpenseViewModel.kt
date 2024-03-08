package com.example.expensetracker.presentation.add_update_expense

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.common.toExpenseData
import com.example.expensetracker.data.db.entities.Category
import com.example.expensetracker.domain.usecases.UseCase
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseState.Companion.INVALID_ADD_UPDATE_EXPENSE_STATE
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.INVALID_EXPENSE_DATA
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

    private var _addExpenseState = mutableStateOf(INVALID_ADD_UPDATE_EXPENSE_STATE)
    val addExpenseState: State<AddUpdateExpenseState> = _addExpenseState

    private var _expenseData = mutableStateOf(INVALID_EXPENSE_DATA)

    var categories = Category.entries.toList()

    fun addExpense(expense: ExpenseData) = viewModelScope.async {
        val isExpenseInserted = useCase.addOrUpdateExpense(expense.toExpenseEntity(
            _addExpenseState.value.isUpdate
        ))
        _addExpenseState.value = INVALID_ADD_UPDATE_EXPENSE_STATE
        return@async isExpenseInserted
    }

    fun getExpense(expenseId: Int) = viewModelScope.launch {
        val expense = useCase.getExpense(expenseId)
        expense?.let {
            _expenseData.value = expense.toExpenseData()
            _addExpenseState.value.expenseData = _expenseData.value
        }
    }

    fun setExpenseAmount(
        amount: String = "0"
    ) {
       _expenseData.value = _expenseData.value.copy(amount = amount)
        _addExpenseState.value = _addExpenseState.value.copy(expenseData = _expenseData.value)
    }

    fun setExpenseCategory(
        expenseCategory: Category,
    ) {
        _expenseData.value = _expenseData.value.copy(categoryId = expenseCategory.categoryId)
        _addExpenseState.value = _addExpenseState.value.copy(expenseData = _expenseData.value)
    }

    fun setExpenseDescription(
        description: String = ""
    ) {
        _expenseData.value = _expenseData.value.copy(description = description)
        _addExpenseState.value = _addExpenseState.value.copy(expenseData = _expenseData.value)
    }

    fun resetState() {
        _addExpenseState.value = INVALID_ADD_UPDATE_EXPENSE_STATE.copy(
            expenseData = INVALID_EXPENSE_DATA.copy(id= 0, title = "", description = "", amount = "", categoryId = -1, date = ""),
            isUpdate = false
        )
        Log.i(TAG, "resetState: ${_addExpenseState.value}")
    }

    companion object{
        private const val TAG = "AddUpdateExpenseViewMod"
    }
}