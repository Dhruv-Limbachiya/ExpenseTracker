package com.example.expensetracker.presentation.expense_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.expensetracker.R
import com.example.expensetracker.presentation.ui.theme.openSansBoldFontFamily

@Composable
fun NoExpenseFound(modifier: Modifier = Modifier,showAddExpenseButton: Boolean = true ,onAddExpenseButtonClicked: () -> Unit) {
    val noRecordFoundComposition by rememberLottieComposition(spec= LottieCompositionSpec.RawRes(R.raw.no_record_found))
    val progress by animateLottieCompositionAsState(noRecordFoundComposition, iterations = LottieConstants.IterateForever)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = noRecordFoundComposition, progress = { progress })
        Text(text = "No Expense Found!", fontFamily = openSansBoldFontFamily)
        Spacer(modifier = Modifier.height(4.dp))
        if(showAddExpenseButton){
            OutlinedButton(onClick = onAddExpenseButtonClicked) {
                Text(text = "Add Expense")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoExpenseFoundPreview() {
    NoExpenseFound {

    }
}