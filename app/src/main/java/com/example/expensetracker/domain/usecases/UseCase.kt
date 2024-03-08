package com.example.expensetracker.domain.usecases

data class UseCase(
    val getExpenses: GetExpenses,
    val getExpense: GetExpense,
    val addOrUpdateExpense: AddOrUpdateExpense,
    val removeExpense: RemoveExpense,
    val getCurrentMonthTotalSpent: GetCurrentMonthTotalSpent
)