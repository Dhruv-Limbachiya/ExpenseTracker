package com.example.expensetracker

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.expensetracker.navigation.AppNavHost
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

    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()

//        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            // enable navController to navigate b/w composable
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//            AppNavHost(navHostController = navController as TestNavHostController)
//        }
    }


    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.onNodeWithContentDescription("DashboardScreen").assertIsDisplayed()
    }

    fun navigate_fromDashboardToExpenseListScreen_expectedSuccess() {
        // nav host
        // nav controller
        // navigate
    }
}