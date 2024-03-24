package com.dhruvv.expensetracker.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruvv.expensetracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit),
) {
    TopAppBar(title = { 
      Text(text = title, fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.open_sans_bold,)) )
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
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }
    })
}