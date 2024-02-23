package com.example.expensetracker.presentation.add_update_expense

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUpdateExpenseScreen(
    modifier: Modifier = Modifier,
//    viewModel: AddUpdateExpenseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            ExpenseTrackerAppBar(
                title = context.getString(R.string.add_amount_heading),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }

    ) { innerPadding ->
        AddUpdateExpenseForm(modifier = modifier.padding(innerPadding))
    }
}

@Composable
fun AddUpdateExpenseForm(
    modifier: Modifier = Modifier
) {
    var amount by remember { mutableStateOf("") }
    var currentExpenseType by remember { mutableStateOf("Tea & Snacks") }
    var description by remember { mutableStateOf("") }

    Column {
        AmountTextField(
            modifier = modifier,
            amount = amount,
            onAmountChange = { changeAmt ->
                amount = changeAmt
            }
        )
        ExpenseCategory(modifier = modifier, currentExpenseType = currentExpenseType)
        ExpenseDescriptionField(modifier = modifier, description = description, onDescriptionChange = {
            description = it
        })
    }
}

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: String,
    onAmountChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = if (amount == "") {
            ""
        } else {
            amount
        },
        label = {
            Text(text = "Amount")
        },
        onValueChange = { onAmountChange(it) },
        prefix = {
            Text(text = "₹", fontSize = 24.sp)
        },
        suffix = {
            Text(text = "INR")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun ExpenseCategory(modifier: Modifier = Modifier, currentExpenseType: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = context.getString(R.string.expense_made_for), style = MaterialTheme.typography.bodyLarge)
        Text(text = currentExpenseType)
    }
}

@Composable
fun ExpenseDescriptionField(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionChange: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = description,
        label = {
            Text(text = "Description")
        },
        onValueChange = { onDescriptionChange(it) },
        maxLines = 5,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
    )
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun AddUpdateExpenseFormPreview() {
    AddUpdateExpenseForm()
    ExpenseCategory(currentExpenseType = "Tea & Snacks")
}
