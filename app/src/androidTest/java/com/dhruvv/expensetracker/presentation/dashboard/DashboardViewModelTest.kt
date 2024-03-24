package com.dhruvv.expensetracker.presentation.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.dhruvv.expensetracker.data.db.dao.ExpenseDao
import com.dhruvv.expensetracker.data.db.entities.Category
import com.dhruvv.expensetracker.data.db.entities.Expense
import com.dhruvv.expensetracker.data.db.room.ExpenseTrackerDB
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository
import com.dhruvv.expensetracker.domain.usecases.UseCase
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class DashboardViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var expenseDao: ExpenseDao

    @Inject
    @Named("test_use_case")
    lateinit var useCase: UseCase

    @Inject
    @Named("expense_tracker_test_db")
    lateinit var expenseTrackerDB: ExpenseTrackerDB

    lateinit var viewModel: DashboardViewModel

    @Inject
    @Named("fake_expense_tracker_repository")
    lateinit var fakeRepository: ExpenseRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        expenseDao = expenseTrackerDB.getExpenseDao()
        viewModel = DashboardViewModel(useCase)
    }

    @After
    fun tearDown() {
        expenseTrackerDB.close()
    }


    @Test
    fun fetchExpenses_whenGetExpenseUseCaseInvoked_shouldReturnSuccess() {
        runBlocking {
            // arrange
            val expense = Expense(
                id =1,
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2022-01-01",
            )
            fakeRepository.insertExpense(expense)

            launch {
                delay(1000)

                val burger = Expense(
                    id =2,
                    title = "Burger",
                    description = "Double Patty Veg Burger",
                    amount = 199.0,
                    categoryId = Category.FOOD_CATEGORY.categoryId,
                    date = "2022-01-01",
                )
                fakeRepository.insertExpense(burger)
            }

            // act
            viewModel.getAllExpenses()

            fakeRepository.getAllExpenses().test {
                awaitItem()
                awaitItem()
                val dashboard = viewModel.dashboardState.value
                // assert
                assertThat(dashboard.expenses.size).isEqualTo(2)
            }

        }
    }

    @Test
    fun getTotalAmount_whenGetCurrentMonthTotalInvoked_returnCorrectAmount(){
        runBlocking {
            // arrange
            val pizza = Expense(
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-02-01",
            )

            val burger = Expense(
                title = "Burger",
                description = "Double Patty Veg Burger",
                amount = 199.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-02-10",
            )

            val dhosa = Expense(
                title = "dhosa",
                description = "Mysore Masala Dhosa",
                amount = 275.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-01-01",
            )

            fakeRepository.insertExpense(pizza)
            fakeRepository.insertExpense(burger)
            fakeRepository.insertExpense(dhosa)

            // act
            viewModel.getCurrentMonthTotalSpent()

            // assert
            fakeRepository.getCurrentMonthExpenses().test {
                awaitItem()
                val currentMonthTotalSpent = viewModel.dashboardState.value.totalExpenses
                assertThat(currentMonthTotalSpent).isEqualTo(434.6)
            }
        }
    }

    @Test
    fun getTotalSpent_whenThereIsNoSpentExists_returnZero(){
        runBlocking {
            // arrange
            val pizza = Expense(
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            val burger = Expense(
                title = "Burger",
                description = "Double Patty Veg Burger",
                amount = 199.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-10",
            )

            val dhosa = Expense(
                title = "dhosa",
                description = "Mysore Masala Dhosa",
                amount = 275.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            fakeRepository.insertExpense(pizza)
            fakeRepository.insertExpense(burger)
            fakeRepository.insertExpense(dhosa)

            // act
            viewModel.getCurrentMonthTotalSpent()

            // assert
            fakeRepository.getCurrentMonthExpenses().test {
                awaitItem()
                val currentMonthTotalSpent = viewModel.dashboardState.value.totalExpenses
                assertThat(currentMonthTotalSpent).isEqualTo(0)
            }
        }
    }

}