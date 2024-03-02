package com.example.expensetracker.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.R
import com.example.expensetracker.common.formatDoubleWithCommas
import com.example.expensetracker.common.toExpenseData
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.example.expensetracker.presentation.expense_list.ExpenseItem
import com.example.expensetracker.presentation.expense_list.ExpenseList
import com.example.expensetracker.presentation.ui.theme.lightGray
import com.example.expensetracker.presentation.ui.theme.mediumGray
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val totalSpent = viewModel.dashboardState.value.totalExpenses
    val expenses = viewModel.dashboardState.value.expenses

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
    ) { innerPaddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPaddingValues),
            verticalArrangement = Arrangement.Center,
        ) {
            TotalExpense(modifier = modifier,totalSpent)

            Spacer(modifier = Modifier.height(30.dp))

            Expenses(modifier = modifier, expenses = expenses)
        }
    }
}

@Composable
fun TotalExpense(modifier: Modifier = Modifier, totalSpent: Double) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(top = 16.dp)
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(28),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 30.dp, top = 16.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_rupee_symbol_vector),
                colorFilter = ColorFilter.tint(color = Color.White),
                contentDescription = "Rupee",
                )

            Text(
                modifier = modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                text = totalSpent.formatDoubleWithCommas(), fontFamily = openSansBoldFontFamily, fontSize = 42.sp, color = Color.White, style = TextStyle(platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
                )
            )

            Text(
                text = "INR",
                fontFamily = openSansBoldFontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(top = 24.dp, end = 36.dp)
            )
        }
    }
}

@Composable
fun Expenses(modifier: Modifier=Modifier, expenses: List<Expense>) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "All Expenses",
            fontFamily = openSansBoldFontFamily,
        )
        Box(
            modifier = Modifier
                .shadow(shape = RoundedCornerShape(20.dp), elevation = 1.dp)
                .background(lightGray)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .clickable { },
                text = "View all",
                fontFamily = openSansBoldFontFamily,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }

    ExpenseList(
        expenses = expenses,
        showTillYesterday = true
    )
}

@Preview(showSystemUi = true)
@Composable
private fun DashboardPreview() {
    DashboardScreen()
}

