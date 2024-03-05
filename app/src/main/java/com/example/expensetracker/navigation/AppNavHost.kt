package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.add_update_expense.AddUpdateExpenseScreen
import com.example.expensetracker.presentation.expense_list.ExpenseListScreen
import com.example.expensetracker.presentation.main.DashboardScreen

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
           DashboardScreen(modifier)
       }

       composable(route = Destination.AddUpdateExpenseScreen.route) {
           AddUpdateExpenseScreen() {

           }
       }

       composable(route = Destination.ExpenseListScreen.route) {
           ExpenseListScreen()
       }
   }
}