package com.example.expensetracker.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit),
) {
    TopAppBar(title = { 
      Text(text = title )
    },
        navigationIcon = navigationIcon
    )
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun ExpenseTrackerAppBarPreview() {
    ExpenseTrackerAppBar(title = "Add Amount", navigationIcon = {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    })
}