package com.example.expensetracker.presentation.expense_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.common.isYesterday
import com.example.expensetracker.common.toDate
import com.example.expensetracker.common.toExpenseData
import com.example.expensetracker.common.toddMMMyyyy
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily
import kotlin.random.Random

private const val TAG = "ExpenseList"
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseList(
    modifier: Modifier = Modifier,
    expenses: List<Expense> = emptyList(),
    showTillYesterday: Boolean = false,
    onExpenseItemClick:(Int) -> Unit
    ) {
    
    val dateWiseExpenseMap = mutableMapOf<String, MutableList<Expense>>()

    expenses.forEach {
        if(it.date == System.currentTimeMillis().toDate()) {
            dateWiseExpenseMap.getOrPut("Today") { mutableListOf() }.add(it)  /// today's expenses
        }else if(it.date?.isYesterday() == true){
            dateWiseExpenseMap.getOrPut("Yesterday"){ mutableListOf() }.add(it) /// yesterday
        }else {
            if(!showTillYesterday) {
                dateWiseExpenseMap.getOrPut(it.date?.toddMMMyyyy() ?: ""){ mutableListOf() }.add(it) /// other day
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        dateWiseExpenseMap.keys
            .forEach { date ->
            stickyHeader {
                Text(text = date,
                    modifier = Modifier.fillMaxWidth().background(color = Color.White).padding(4.dp),
                    fontFamily = openSansBoldFontFamily,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            items(dateWiseExpenseMap[date]?.toList() ?: emptyList()) {
                ExpenseItem(modifier = Modifier.testTag("expenseItem"),expenseData = it.toExpenseData(), onExpenseItemClick = onExpenseItemClick,)
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpenseListPreview() {
    ExpenseList(expenses = generateRandomExpenseList(), onExpenseItemClick = {

    })
}

fun generateRandomExpenseList(): List<Expense> {
    val expenseList = mutableListOf<Expense>()

    for (i in 1..10) {
        val expense = Expense(
            title = "Expense $i",
            description = "Description for Expense $i",
            amount = Random.nextDouble(1.0, 100.0),
            categoryId = Random.nextInt(1, 5),
            date = "2024-0$i-02" // You can replace this with a function to generate random dates
        )
        expenseList.add(expense)
    }

    return expenseList
}
