package com.example.expensetracker.presentation.expense_list

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseForm
import com.example.expensetracker.presentation.add_update_expense.saveExpense
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar
import kotlinx.coroutines.launch

@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier,
    expenses: List<Expense>
) {

    val context = LocalContext.current

    Scaffold(
        snackbarHost = { },
        topBar = {
            ExpenseTrackerAppBar(
                title = context.getString(R.string.expenses),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        ExpenseList(modifier = modifier.padding(innerPadding), expenses = expenses)
    }
}