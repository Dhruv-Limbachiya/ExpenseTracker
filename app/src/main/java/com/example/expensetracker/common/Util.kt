package com.example.expensetracker.common

import android.os.Build
import androidx.navigation.NavController
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toDate(): String {
    val formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.format(this)
    }

    return formattedDate ?: getCalendarDate()
}

fun String.toddMMMyyyy(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(this) ?: Date()
    val formattedDate = outputFormat.format(date)
    return formattedDate
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


fun String.isYesterday(): Boolean {
    // Parse the string date
    val dateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
    val parsedDate = dateFormat.parse(this)

    // Get Calendar instances for today and the parsed date
    val todayCalendar = Calendar.getInstance()
    val parsedCalendar = Calendar.getInstance()

    parsedDate?.let {
        parsedCalendar.time = parsedDate
    }

    // Check if parsed date is yesterday
    todayCalendar.add(Calendar.DAY_OF_YEAR, -1) // Move to yesterday
    return todayCalendar.get(Calendar.YEAR) == parsedCalendar.get(Calendar.YEAR) &&
            todayCalendar.get(Calendar.DAY_OF_YEAR) == parsedCalendar.get(Calendar.DAY_OF_YEAR)
}

fun getYesterdayDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -3) // Subtract 1 to get yesterday

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(calendar.time)
}


