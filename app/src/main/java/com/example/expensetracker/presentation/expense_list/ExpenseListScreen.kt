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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.R
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseForm
import com.example.expensetracker.presentation.add_update_expense.saveExpense
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.example.expensetracker.presentation.dashboard.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToAddUpdateExpenseScreen: (Int) -> Unit,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val expenses = viewModel.dashboardState.value.expenses

    Scaffold(
        modifier = Modifier.semantics { contentDescription = "ExpenseListScreen" },
        snackbarHost = { },
        topBar = {
            ExpenseTrackerAppBar(
                title = context.getString(R.string.expenses),
                navigationIcon = {
                    IconButton(onClick = onBackPress, modifier = Modifier.testTag("ExpenseBack")) {
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

        if(expenses.isEmpty()) {
            NoExpenseFound(modifier = modifier, onAddExpenseButtonClicked = {
                navigateToAddUpdateExpenseScreen(-1)
            })
        } else {
            ExpenseList(modifier = Modifier.padding(innerPadding), expenses = expenses, onExpenseItemClick = navigateToAddUpdateExpenseScreen, onExpenseItemSwipeToDelete = {
                viewModel.deleteExpense(it)
            })
        }
    }
}