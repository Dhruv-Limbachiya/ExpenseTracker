package com.dhruvv.expensetracker.presentation.add_update_expense

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.dhruvv.expensetracker.presentation.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddUpdateExpenseScreenKtTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup(){
        hiltRule.inject()

        composeTestRule.activity.setContent {
            AddUpdateExpenseScreen {

            }
        }
    }

    @Test
    fun test_AddUpdateExpenseScreen_isDisplayed() {
        composeTestRule.onRoot().printToLog(TAG)
        composeTestRule.onNodeWithContentDescription("AddUpdateScreen").assertExists("Add Update Screen is not visible")
    }

    @Test
    fun addUpdateScreenTest_whenAmountIsBlank_shouldShowAmountIsEmptyValidationMessage() {
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()
        composeTestRule.onNodeWithText("Amount can't be empty").assertExists()
    }

    @Test
    fun addUpdateScreenTest_wheAmountIsNegative_shouldShowInvalidValueMessage() {
        composeTestRule.onNodeWithTag("amount_text_field").performTextInput("-52")
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()
        composeTestRule.onRoot().printToLog(TAG)
        composeTestRule.onNodeWithText("Negative values are not allowed!").assertExists()
    }

    @Test
    fun addUpdateScreenTest_whenDescriptionIsBlank_shouldShowDescriptionIsEmptyValidationMessage() {
        composeTestRule.onNodeWithTag("amount_text_field").performTextInput("152")
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()
        composeTestRule.onNodeWithText("Description can't be empty").assertExists()
    }

    companion object {
        private const val TAG = "AddUpdateExpenseScreenK"
    }
}