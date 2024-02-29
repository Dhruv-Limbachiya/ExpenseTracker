package com.example.expensetracker.presentation.expense_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.common.toDate
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.getCategory
import com.example.expensetracker.presentation.ui.theme.mediumGray
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily
import com.example.expensetracker.presentation.ui.theme.purplePrimary
import kotlin.math.exp

@Composable
fun ExpenseItem(modifier: Modifier = Modifier, expenseData: ExpenseData) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = mediumGray),
        shape = RoundedCornerShape(20.dp)

    ) {
        Row(
            modifier = modifier.clickable {  },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp,top = 8.dp, bottom = 8.dp)
                    .shadow(shape = RoundedCornerShape(18.dp), elevation = 2.dp)
                    .background(purplePrimary)
                    .padding(12.dp)
            ) {
                Image(
                    imageVector = Icons.Outlined.Mail,
                    colorFilter = ColorFilter.tint(color = Color.White),
                    contentDescription = "Icon"
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            ExpenseTitleDescription(modifier.weight(1f).padding(end = 10.dp, ), category = expenseData.getCategory(), description = expenseData.description.toString())

            ExpenseAmount(modifier.padding(end = 30.dp),expenseData.amount)
        }
    }
}

@Composable
fun ExpenseAmount(modifier: Modifier = Modifier, amount: String) {
    Text(modifier = modifier, text = "₹ $amount", color = Color.Black, fontFamily = openSansBoldFontFamily)
}

@Composable
fun ExpenseTitleDescription(modifier: Modifier = Modifier,category:String,description: String) {
    Column(modifier = modifier) {
        Text(text = category , color = Color.Black, fontFamily = openSansBoldFontFamily, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = description, color = Color.Gray, fontFamily = openSansBoldFontFamily,maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpenseItemPreview() {
    ExpenseItem(expenseData = ExpenseData(
        id = 1,
        title = "Grocery Shopping",
        description = "Bought essentials Bought essentials Bought essentials",
        amount = "75.50",
        categoryId = 3, // Assuming this corresponds to the Food category
        date = "29-02-2024"
    ))
}