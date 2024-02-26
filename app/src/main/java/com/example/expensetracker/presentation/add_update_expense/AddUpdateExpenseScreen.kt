@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.expensetracker.presentation.add_update_expense

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardAuxButton
import com.davidmiguel.numberkeyboard.NumberKeyboardButton
import com.davidmiguel.numberkeyboard.data.NumberKeyboardData
import com.davidmiguel.numberkeyboard.listener.NumberKeyboardListener
import com.example.expensetracker.R
import com.example.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.example.expensetracker.presentation.ui.theme.lightGray
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily
import com.example.expensetracker.presentation.ui.theme.purplePrimary


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
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, contentColor = Color.White, containerColor = purplePrimary) {
                Icon(imageVector = Icons.Rounded.AddTask, contentDescription = "Add Expense")
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        AddUpdateExpenseForm(modifier = modifier.padding(innerPadding))
    }
}

@Composable
fun AddUpdateExpenseForm(
    modifier: Modifier = Modifier
) {
    var amount by remember { mutableStateOf("") }
    var currentExpenseType by remember { mutableStateOf(expenseCategory[0]) }
    var description by remember { mutableStateOf("") }

    Column {
        AmountTextField(
            modifier = modifier,
            amount = amount,
            onAmountChange = { changeAmt ->
                amount = changeAmt
            }
        )

        ExpenseCategory(
            modifier = modifier,
            currentExpenseType = currentExpenseType,
            onExpenseTypeChange = {
                currentExpenseType = it
            })

        ExpenseDescriptionField(
            modifier = modifier,
            description = description,
            onDescriptionChange = {
                description = it
            })

//        val buttonModifier = Modifier
//            .aspectRatio(2F)
//            .size(40.dp)
//            .weight(1F)
//
//        Box(
//            modifier = modifier.weight(1f),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            NumPad(
//                modifier = modifier,
//                onValueChanged = {
//                    amount = it
//                },
//                buttonModifier = buttonModifier
//            )
//        }

    }
}

@Composable
fun NumPad(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    buttonModifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val buttonTextStyle = TextStyle(fontFamily = openSansBoldFontFamily)

    NumberKeyboard(
        maxAllowedAmount = 99999999.0,
        maxAllowedDecimals = 2,
        decimalSeparator = '.',
        roundUpToMax = false,
        button = { number, clickedListener ->
            NumberKeyboardButton(
                modifier = buttonModifier.size(40.dp),
                textStyle = buttonTextStyle,
                number = number,
                shape = CircleShape,
                listener = clickedListener
            )
        },
        leftAuxButton = { _ ->
            NumberKeyboardAuxButton(
                modifier = buttonModifier,
                textStyle = buttonTextStyle,
                shape = CircleShape,
                imageVector = Icons.AutoMirrored.Rounded.Backspace,
                clicked = { Toast.makeText(context, "Triggered", Toast.LENGTH_SHORT).show() }
            )
        },
        rightAuxButton = { clickedListener ->
            NumberKeyboardAuxButton(
                modifier = buttonModifier.size(30.dp),
                textStyle = buttonTextStyle,
                imageVector = Icons.Rounded.CheckCircleOutline,
                shape = CircleShape,
                clicked = { clickedListener.onRightAuxButtonClicked() }
            )
        },
        listener = object : NumberKeyboardListener {
            override fun onUpdated(data: NumberKeyboardData) {
                onValueChanged(data.int.toString())
            }
        }
    )
}

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: String,
    onAmountChange: (String) -> Unit
) {

    // initialize focus reference to be able to request focus programmatically
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester = focusRequester),
        textStyle = TextStyle(fontSize = 40.sp, fontFamily = openSansBoldFontFamily),
        value = if (amount == "") {
            ""
        } else {
            amount
        },
        label = {
            Text(text = "Amount", fontFamily = openSansBoldFontFamily, fontSize = 14.sp, color = Color.Gray)
        },
        placeholder = {
            Text(
                text = "0",
                fontFamily = openSansBoldFontFamily,
                fontSize = 40.sp,
                color = Color.Gray
            )
        },
        onValueChange = { onAmountChange(it) },
        prefix = {
            Text(
                text = "₹",
                fontSize = 30.sp,
                fontFamily = openSansBoldFontFamily,
                modifier = Modifier.padding(top = 12.dp, end = 8.dp)
            )
        },
        suffix = {
            Text(
                text = "INR",
                fontFamily = openSansBoldFontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(top = 24.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Gray,
            cursorColor = Color.Black
        ),
    )
}

val expenseCategory = listOf("Household", "Transportation", "Food & Beverages", "Clothing","Personal Care","Entertainment","Gifts","Charity","Miscellaneous")

@Composable
fun ExpenseCategory(
    modifier: Modifier = Modifier,
    currentExpenseType: String,
    onExpenseTypeChange: (String) -> Unit
) {
    val context = LocalContext.current

    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Column {
            Text(
                text = context.getString(R.string.expense_made_for),
                fontFamily = openSansBoldFontFamily,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = currentExpenseType,
                fontFamily = openSansBoldFontFamily,
                modifier = Modifier.padding(top = 6.dp)
            )

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(0.7f),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                expenseCategory.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            onExpenseTypeChange(city)
                            expanded = false // Close menu after selection
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(lightGray)
                .clickable {
                    expanded = expanded.not()
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if(expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = "Drop Down",
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDescriptionField(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionChange: (String) -> Unit
) {

    val context = LocalContext.current

    val interactionSource = remember { MutableInteractionSource() }

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = context.getString(R.string.expense_made_for),
            fontFamily = openSansBoldFontFamily,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(6.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = description,
            textStyle = TextStyle(fontFamily = openSansBoldFontFamily,),
            onValueChange = { onDescriptionChange(it) },
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = description,
                    placeholder = { Text(text = "Enter your description", fontFamily = openSansBoldFontFamily, fontSize = 14.sp,)},
                    innerTextField = it,
                    enabled = true,
                    singleLine = false,
                    contentPadding = PaddingValues(0.dp),
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            },
        )
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun AddUpdateExpenseFormPreview() {
    AddUpdateExpenseScreen()
}
