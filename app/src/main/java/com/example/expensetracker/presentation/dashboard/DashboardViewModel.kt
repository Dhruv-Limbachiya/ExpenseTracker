package com.example.expensetracker.presentation.dashboard

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.domain.usecases.UserCase
import com.example.expensetracker.presentation.dashboard.DashboardState.Companion.INVALID_DASHBOARD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

//@HiltViewModel
class DashboardViewModel constructor(
//    @Named("main_use_case") private val useCase: UserCase
     private val useCase: UserCase
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

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}