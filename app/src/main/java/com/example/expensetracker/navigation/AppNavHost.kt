package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseScreen
import com.example.expensetracker.presentation.expense_list.ExpenseListScreen
import com.example.expensetracker.presentation.main.DashboardScreen
import kotlin.math.exp

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
   NavHost(
       navController = navHostController,
       modifier = modifier,
       startDestination = Destination.DashboardScreen.route
   ) {
       composable(route = Destination.DashboardScreen.route) {
           DashboardScreen {
               // navigate to expense list screen
               navHostController.navigate(Destination.ExpenseListScreen.route)
           }
       }

       composable(route = "${Destination.AddUpdateExpenseScreen.route}?id={expenseId}", arguments = listOf(
           navArgument(name = "expenseId") {
               type = NavType.IntType
               defaultValue = -1
           }
       )) {backStackEntry ->
           val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: -1
           AddUpdateExpenseScreen(expenseId = expenseId) {
               navHostController.popBackStack()
           }
       }

       composable(route = Destination.ExpenseListScreen.route) {
           ExpenseListScreen { expenseId ->
               navHostController.navigate("${Destination.AddUpdateExpenseScreen.route}?id=${expenseId}")
           }
       }
   }
}