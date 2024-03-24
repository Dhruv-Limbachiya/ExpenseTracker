@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.dhruvv.expensetracker.presentation.add_update_expense

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardAuxButton
import com.davidmiguel.numberkeyboard.NumberKeyboardButton
import com.davidmiguel.numberkeyboard.data.NumberKeyboardData
import com.davidmiguel.numberkeyboard.listener.NumberKeyboardListener
import com.dhruvv.expensetracker.R
import com.dhruvv.expensetracker.data.db.entities.Category
import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData.Companion.getCategory
import com.dhruvv.expensetracker.presentation.common.ExpenseTrackerAppBar
import com.dhruvv.expensetracker.presentation.ui.theme.lightGray
import com.dhruvv.expensetracker.presentation.ui.theme.openSansBoldFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "AddUpdateExpenseScreen"

@Composable
fun AddUpdateExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: AddUpdateExpenseViewModel = hiltViewModel(),
    expenseId: Int = -1,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }

    BackHandler {
        viewModel.resetState()
        onBack()
    }

    LaunchedEffect(key1 = Unit) {
        if(expenseId != -1) {
            viewModel.getExpense(expenseId)
        }else{
            viewModel.resetState()
        }
    }

    Scaffold(
        modifier = Modifier.semantics { contentDescription = "AddUpdateScreen" },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ExpenseTrackerAppBar(
                title = context.getString(R.string.add_amount_heading),
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.resetState()
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = 76.dp,
                        minHeight = 76.dp,
                    ),
                onClick = {
                    coroutineScope.launch {
                        if (isValidDetails(viewModel, snackBarHostState, coroutineScope)) {
                            saveExpense(viewModel, coroutineScope) { isSaved ->
                                coroutineScope.launch {
                                    val message =
                                        if (isSaved) "Expense Saved!" else "Failed to save your expense"
                                    snackBarHostState.showSnackbar(message)
                                    onBack()
                                }
                            }
                        }
                    }
                }) {
                Icon(imageVector = Icons.Rounded.AddTask, contentDescription = "Add Expense")
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        AddUpdateExpenseForm(modifier = modifier.padding(innerPadding), viewModel = viewModel)
    }
}

suspend fun isValidDetails(
    viewModel: AddUpdateExpenseViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) = coroutineScope.async {
    if (viewModel.addExpenseState.value.expenseData.amount.isEmpty()) {
        snackBarHostState.showSnackbar("Amount can't be empty")
        return@async false
    } else if(viewModel.addExpenseState.value.expenseData.amount.toInt() < 0) {
        snackBarHostState.showSnackbar("Negative values are not allowed!")
        return@async false
    }else if(viewModel.addExpenseState.value.expenseData.description.isNullOrEmpty()) {
        snackBarHostState.showSnackbar("Description can't be empty")
        return@async false
    }

    return@async true
}.await()

fun saveExpense(
    viewModel: AddUpdateExpenseViewModel,
    coroutineScope: CoroutineScope,
    onSaved: (Boolean) -> Unit = {}
) {

    val expenseData = viewModel.addExpenseState.value.expenseData

    coroutineScope.launch(Dispatchers.IO) {
        val isSaved = viewModel.addExpense(expense = expenseData).await()
        viewModel.resetState()
        withContext(Dispatchers.Main) {
            onSaved(isSaved)
        }
    }
}

@Composable
fun AddUpdateExpenseForm(
    modifier: Modifier = Modifier,
    viewModel: AddUpdateExpenseViewModel
) {

    val amount = viewModel.addExpenseState.value.expenseData.amount
    val description = viewModel.addExpenseState.value.expenseData.description ?: ""

    val categories = viewModel.categories
    val category = viewModel.addExpenseState.value.expenseData.getCategory()


    Column {
        AmountTextField(
            modifier = modifier,
            amount = if (amount.isEmpty()) "" else amount.toString(),
            onAmountChange = { changedAmt ->
                viewModel.setExpenseAmount(changedAmt)
            }
        )

        ExpenseCategory(
            modifier = modifier,
            categories = categories,
            currentExpenseType = category,
            onExpenseTypeChange = { category ->
                viewModel.setExpenseCategory(category)
            })

        ExpenseDescriptionField(
            modifier = modifier,
            description = description,
            onDescriptionChange = {
                viewModel.setExpenseDescription(it)
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
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: String,
    onAmountChange: (String) -> Unit
) {

    // initialize focus reference to be able to request focus programmatically
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier.testTag("amount_text_field")
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
            Text(
                text = "Amount",
                fontFamily = openSansBoldFontFamily,
                fontSize = 14.sp,
                color = Color.Gray
            )
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

@Composable
fun ExpenseCategory(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    currentExpenseType: String,
    onExpenseTypeChange: (Category) -> Unit
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
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.categoryName) },
                        onClick = {
                            onExpenseTypeChange(category)
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
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
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
            textStyle = TextStyle(fontFamily = openSansBoldFontFamily),
            onValueChange = { onDescriptionChange(it) },
            maxLines = 5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = description,
                    placeholder = {
                        Text(
                            text = "Enter your description",
                            fontFamily = openSansBoldFontFamily,
                            fontSize = 14.sp,
                        )
                    },
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


@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
private fun AddUpdateExpenseFormPreview() {
    AddUpdateExpenseScreen() {

    }
}

