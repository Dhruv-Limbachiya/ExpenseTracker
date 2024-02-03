package com.example.expensetracker.presentation.dashboard

import com.example.expensetracker.data.db.entities.Expense

data class DashboardState(
    var totalExpenses: Double = 0.0,
    var expenses: List<Expense> = emptyList()
) {
    companion object {
        val INVALID_DASHBOARD = DashboardState()
    }
}
