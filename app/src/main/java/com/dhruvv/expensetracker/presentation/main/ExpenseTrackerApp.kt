package com.dhruvv.expensetracker.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dhruvv.expensetracker.navigation.AppNavHost

@Composable
fun ExpenseTrackerApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavHost(modifier.padding(innerPadding))
        }
    }
}
