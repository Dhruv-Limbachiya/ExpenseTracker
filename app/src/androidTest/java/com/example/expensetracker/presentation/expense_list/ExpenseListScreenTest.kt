package com.example.expensetracker.presentation.expense_list

import android.util.Log
import androidx.activity.compose.setContent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeLeft
import androidx.core.app.FrameMetricsAggregator.ANIMATION_DURATION
import com.example.expensetracker.data.db.entities.Category
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.repositories.ExpenseRepository
import com.example.expensetracker.domain.usecases.UseCase
import com.example.expensetracker.presentation.dashboard.DashboardViewModel
import com.example.expensetracker.presentation.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class ExpenseListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    @Named("test_use_case")
    lateinit var useCase: UseCase

    lateinit var viewModel: DashboardViewModel

    @Inject
    @Named("fake_expense_tracker_repository")
    lateinit var fakeRepository: ExpenseRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        viewModel = DashboardViewModel(useCase)
        Log.i(TAG, "setUp: Use case : $useCase")

        composeTestRule.activity.setContent {
            ExpenseListScreen(
                viewModel = viewModel,
                navigateToAddUpdateExpenseScreen = {},
                onBackPress = {}
            )
        }
    }

    // test case
    // 1.  is correct screen is displayed
    // 2.  is no expense found displayed on no expenses
    // 3.  swipe to delete remove the expense
    // 4.  click on expense open the add update screen

    @Test
    fun test_expenseListScreen_isDisplayed() {
        composeTestRule.onNodeWithContentDescription("ExpenseListScreen").isDisplayed()
    }


    @Test
    fun test_whenExpenseListEmpty_shouldDisplayNoExpensesFound() {
        composeTestRule.onNodeWithText("No Expense Found!", useUnmergedTree = true).assertExists()
    }

    @Test
    fun test_whenExpenseListIsNotEmpty_expenseListSizeGreaterThanZero() {
        addFewExpenses()

        composeTestRule.onNode(
            hasParent(hasContentDescription("ExpenseListScreen")) and hasTestTag(
                "expense_list"
            )
        ).assertExists()

        composeTestRule.onAllNodes(hasTestTag("expenseItem")).assertCountEquals(3)

    }

    @Test
    fun test_swipeExpenseItem_shouldRemoveExpense() {
        runBlocking {
            // Add some expenses to the UI
            addFewExpenses()

            // Ensure the UI is idle before starting the test
            composeTestRule.waitForIdle()

            // Disable auto-advancing the main clock to manually control animations
            composeTestRule.mainClock.autoAdvance = false

            // Assert that there are initially 3 expense items in the list
            composeTestRule.onAllNodes(hasTestTag("expenseItem"), useUnmergedTree = true).assertCountEquals(3)

            // Assert that the specific expense item with tag "Expense : 3" exists
            composeTestRule.onNodeWithTag("Expense : 3", useUnmergedTree = true).assertExists()

            // Swipe left on the first expense item
            val itemToRemove = composeTestRule.onNodeWithTag("Expense : 3", useUnmergedTree = true)
            itemToRemove.performTouchInput {
                swipeLeft()
            }

            // Advance the main clock by the duration of the swipe animation plus a buffer of 5 seconds
            composeTestRule.mainClock.advanceTimeBy(ANIMATION_DURATION.toLong() + 5L)

            // Re-enable auto-advancing the main clock for subsequent tests
            composeTestRule.mainClock.autoAdvance = true

            // Print the UI hierarchy to logs for debugging purposes
            composeTestRule.onRoot(false).printToLog("Again $TAG")

            // Assert that the expense item with tag "Expense : 3" no longer exists after swiping left
            composeTestRule.onNodeWithTag("Expense : 3", useUnmergedTree = true).assertDoesNotExist()
        }
    }

    private fun addFewExpenses() {
        runBlocking {
            // arrange
            val pizza = Expense(
                id = 1,
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            val burger = Expense(
                id = 2,
                title = "Burger",
                description = "Double Patty Veg Burger",
                amount = 199.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-10",
            )

            val dhosa = Expense(
                id = 3,
                title = "dhosa",
                description = "Mysore Masala Dhosa",
                amount = 275.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            fakeRepository.insertExpense(pizza)
            fakeRepository.insertExpense(burger)
            fakeRepository.insertExpense(dhosa)
        }
    }

    companion object {
        private const val TAG = "ExpenseListScreenKtTest"
    }
}