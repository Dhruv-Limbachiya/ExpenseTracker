package com.example.expensetracker.common

import android.os.Build
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

fun Long.toDate(): String {
    val formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        dateFormat.format(this)
    }

    return formattedDate ?: getCalendarDate()
}


fun getCalendarDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1  // Months are 0-indexed
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return String.format("%04d-%02d-%02d", year, month, day)
}

fun Double.formatDoubleWithCommas(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return numberFormat.format(this)
}

fun Expense.toExpenseData(): ExpenseData {
    return ExpenseData(
        id = id,
        title = title,
        description = description,
        amount = amount.toString(),
        categoryId = categoryId,
        date = date
    )
}