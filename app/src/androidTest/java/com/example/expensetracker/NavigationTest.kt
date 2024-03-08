package com.example.expensetracker

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.expensetracker.navigation.AppNavHost
import com.example.expensetracker.navigation.Destination
import com.example.expensetracker.presentation.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@HiltAndroidTest
class NavigationTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private var navController: TestNavHostController? = null

    private var context: Context? = null

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            context = LocalContext.current
            navController = TestNavHostController(context!!)
            // enable navController to navigate b/w composable
            navController?.navigatorProvider?.addNavigator(ComposeNavigator())
            AppNavHost(navHostController = navController!!)
        }

        hiltRule.inject()
    }


    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.onNodeWithTag("Dashboard").assertIsDisplayed()
    }

    @Test
    fun navHost_clickOnViewAll_navigatesToExpenseListScreen() {
        composeTestRule.onNodeWithText("View all").performClick()
        composeTestRule.onNodeWithContentDescription("ExpenseListScreen").assertIsDisplayed()
    }

    @Test
    fun navHost_onExpenseItemClick_navigateToAddUpdateExpenseScreen() {
        composeTestRule.onNodeWithText("View all").performClick()
        composeTestRule.onNodeWithTag("ExpenseBack").performClick()
        composeTestRule.onNodeWithContentDescription("AddUpdateScreen").assertIsDisplayed()
    }

}