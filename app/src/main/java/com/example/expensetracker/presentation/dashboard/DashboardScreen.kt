package com.example.expensetracker.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.example.expensetracker.presentation.ui.theme.purplePrimary

@Composable
fun DashboardComposable(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            ExpenseTrackerAppBar(
                title = context.getString(R.string.dashboard_heading),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Dashboard,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }, contentColor = Color.White, containerColor = purplePrimary) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Expense")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Dashboard")
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun DashboardPreview() {
    DashboardComposable()
}