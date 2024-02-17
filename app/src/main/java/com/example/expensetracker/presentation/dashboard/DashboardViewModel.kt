package com.example.expensetracker.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.usecases.UseCase
import com.example.expensetracker.presentation.dashboard.DashboardState.Companion.INVALID_DASHBOARD
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//@HiltViewModel
class DashboardViewModel constructor(
//    @Named("main_use_case") private val useCase: UserCase
     private val useCase: UseCase
) : ViewModel() {

    init {
        Log.i(TAG, "Use Case : $useCase")
    }

    private var _dashboardState = mutableStateOf(INVALID_DASHBOARD)
    val dashboardState: State<DashboardState> = _dashboardState

    fun getAllExpenses() = viewModelScope.launch {
        useCase.getExpense().collect {
            Log.i(TAG, "getAllExpenses: $it")
            _dashboardState.value = _dashboardState.value.copy(
                expenses = it
            )
        }
    }

    fun getCurrentMonthTotalSpent() = viewModelScope.launch {
        useCase.getCurrentMonthTotalSpent().collectLatest {
            _dashboardState.value = _dashboardState.value.copy(
                totalExpenses = it ?: 0.0
            )
        }
    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}