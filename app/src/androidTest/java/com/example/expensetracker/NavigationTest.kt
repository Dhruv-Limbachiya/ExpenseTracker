package com.example.expensetracker

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.expensetracker.navigation.AppNavHost
import com.example.expensetracker.navigation.Destination
import com.example.expensetracker.presentation.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
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

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            // enable navController to navigate b/w composable
            navController?.navigatorProvider?.addNavigator(ComposeNavigator())
            AppNavHost(navHostController = navController!!)
        }

        hiltRule.inject()
    }


    @Test
    fun navHost_verifyStartDestination() {
        navController?.assertCurrentRouteName(Destination.DashboardScreen.route)
    }

    fun navigate_fromDashboardToExpenseListScreen_expectedSuccess() {
        // nav host
        // nav controller
        // navigate
    }
}