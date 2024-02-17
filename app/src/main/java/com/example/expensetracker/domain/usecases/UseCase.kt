package com.example.expensetracker.domain.usecases

data class UseCase(
    val getExpense: GetExpenses,
    val addOrUpdateExpense: AddOrUpdateExpense,
    val removeExpense: RemoveExpense,
    val getCurrentMonthTotalSpent: GetCurrentMonthTotalSpent
)