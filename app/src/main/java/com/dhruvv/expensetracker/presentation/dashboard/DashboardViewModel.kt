package com.dhruvv.expensetracker.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvv.expensetracker.domain.usecases.UseCase
import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.toExpenseEntity
import com.dhruvv.expensetracker.presentation.dashboard.DashboardState.Companion.INVALID_DASHBOARD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        @Named("main_use_case") private val useCase: UseCase,
    ) : ViewModel() {
        init {
            Log.i(TAG, "Use Case : $useCase")
        }

        private var _dashboardState = mutableStateOf(INVALID_DASHBOARD)
        val dashboardState: State<DashboardState> = _dashboardState

        init {
            getCurrentMonthTotalSpent()
            getAllExpenses()
        }

        fun getAllExpenses() =
            viewModelScope.launch {
                useCase.getExpenses().collect {
                    Log.i(TAG, "getAllExpenses: $it")
                    _dashboardState.value =
                        _dashboardState.value.copy(
                            expenses = it,
                        )
                }
            }

        fun getCurrentMonthTotalSpent() =
            viewModelScope.launch {
                useCase.getCurrentMonthTotalSpent().collectLatest {
                    _dashboardState.value =
                        _dashboardState.value.copy(
                            totalExpenses = it ?: 0.0,
                        )
                }
            }

        fun deleteExpense(expenseData: ExpenseData) =
            viewModelScope.launch {
                useCase.removeExpense(expense = expenseData.toExpenseEntity(true))
            }

        companion object {
            private const val TAG = "DashboardViewModel"
        }
    }
