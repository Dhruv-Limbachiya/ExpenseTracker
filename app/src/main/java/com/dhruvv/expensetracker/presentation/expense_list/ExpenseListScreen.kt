package com.dhruvv.expensetracker.presentation.expense_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhruvv.expensetracker.R
import com.dhruvv.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.dhruvv.expensetracker.presentation.dashboard.DashboardViewModel

@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier.semantics { contentDescription = "ExpenseListScreen" },
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateToAddUpdateExpenseScreen: (Int) -> Unit,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val expenses = viewModel.dashboardState.value.expenses

    Scaffold(
        modifier = modifier,
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