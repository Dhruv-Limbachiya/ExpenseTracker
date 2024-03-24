package com.dhruvv.expensetracker.navigation

sealed class Destination(
    val route: String,
) {
    data object DashboardScreen : Destination(Route.DASHBOARD)
    data object AddUpdateExpenseScreen : Destination(Route.ADD_UPDATE_EXPENSE)
    data object ExpenseListScreen : Destination(Route.EXPENSE_LIST)
}