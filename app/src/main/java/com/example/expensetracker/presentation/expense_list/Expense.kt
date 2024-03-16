package com.example.expensetracker.presentation.expense_list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.getCategory
import com.example.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.getIcon
import com.example.expensetracker.presentation.ui.theme.mediumGray
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily
import com.example.expensetracker.presentation.ui.theme.purplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier, expenseData: ExpenseData,
    onExpenseItemClick: (Int) -> Unit,
    onExpenseItemSwiped: (ExpenseData) -> Unit
) {

    var isSwiped by remember {
        mutableStateOf(false)
    }

    var show by remember {
        mutableStateOf(true)
    }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                isSwiped = true
                show = false
                onExpenseItemSwiped(expenseData)
                true
            } else {
                isSwiped = false
                false
            }

        }
    )


    AnimatedVisibility(visible = show,exit = fadeOut(spring()), modifier = modifier) {
        SwipeToDismissBox(state = dismissState,
            backgroundContent = {
                val color by animateColorAsState(
                    targetValue =
                    when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                        else -> Color.Transparent
                    }, label = ""
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(shape = RoundedCornerShape(20.dp), elevation = 2.dp)
                        .background(color = color),
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Expense"
                    )
                }
            },
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("Expense : ${expenseData.id}"),
                    colors = CardDefaults.cardColors(containerColor = mediumGray),
                    shape = RoundedCornerShape(20.dp)

                ) {
                    Row(
                        modifier = Modifier.clickable { onExpenseItemClick(expenseData.id) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                                .shadow(shape = RoundedCornerShape(18.dp), elevation = 2.dp)
                                .background(purplePrimary)
                                .padding(12.dp)
                        ) {
                            Image(
                                imageVector = expenseData.getIcon(),
                                colorFilter = ColorFilter.tint(color = Color.White),
                                contentDescription = "Icon"
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        ExpenseTitleDescription(
                            Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            category = expenseData.getCategory(),
                            description = expenseData.description.toString()
                        )

                        ExpenseAmount(Modifier.padding(end = 30.dp), expenseData.amount)
                    }
                }
            }
        )
    }

//
//    LaunchedEffect(key1 = isSwiped, key2 = show) {
//        Log.i("Expense", "ExpenseItem: is swiped $isSwiped")
//        if(isSwiped) {
//            onExpenseItemSwiped(expenseData)
//        }
//    }
}

@Composable
fun ExpenseAmount(modifier: Modifier = Modifier, amount: String) {
    Text(
        modifier = modifier,
        text = "₹ $amount",
        color = Color.Black,
        fontFamily = openSansBoldFontFamily
    )
}

@Composable
fun ExpenseTitleDescription(modifier: Modifier = Modifier, category: String, description: String) {
    Column(modifier = modifier) {
        Text(
            text = category,
            color = Color.Black,
            fontFamily = openSansBoldFontFamily,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = description,
            color = Color.Gray,
            fontFamily = openSansBoldFontFamily,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpenseItemPreview() {
    ExpenseItem(
        expenseData = ExpenseData(
            id = 1,
            title = "Grocery Shopping",
            description = "Bought essentials Bought essentials Bought essentials",
            amount = "75.50",
            categoryId = 3, // Assuming this corresponds to the Food category
            date = "29-02-2024"
        ), onExpenseItemClick = {

        }, onExpenseItemSwiped = {

        })
}