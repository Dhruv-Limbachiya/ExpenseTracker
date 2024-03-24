package com.dhruvv.expensetracker.presentation.add_update_expense

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.dhruvv.expensetracker.data.db.entities.Category
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository
import com.dhruvv.expensetracker.domain.usecases.UseCase
import com.dhruvv.expensetracker.presentation.add_update_expense.data.ExpenseData
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class AddUpdateExpenseViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_use_case")
    lateinit var useCase: UseCase

    lateinit var viewModel: AddUpdateExpenseViewModel

    @Inject
    @Named("fake_expense_tracker_repository")
    lateinit var fakeRepository: ExpenseRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = AddUpdateExpenseViewModel(useCase = useCase)
    }

    @Test
    fun insertExpenseDetails_expectedExpenseListIncreasedByOne() {
        runBlocking {
            // arrange
            val pizza = ExpenseData(
                id = 1,
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60.toString(),
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            val pizza1 = ExpenseData(
                id = 2,
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60.toString(),
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            // act
            val isInserted = viewModel.addExpense(pizza).await()
            val isInsertedAgain = viewModel.addExpense(pizza1).await()

            //assert
            assertThat(isInserted).isTrue()
            assertThat(isInsertedAgain).isTrue()

            fakeRepository.getAllExpenses().test {
                val expenses = awaitItem()
                assertThat(expenses.size).isEqualTo(2)
            }
        }
    }


    @Test
    fun updateExpenseData_shouldReturnCorrectUpdateData() {
        runBlocking {
            // arrange
            val pizza = ExpenseData(
                id = 1,
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60.toString(),
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            val pizza1 = ExpenseData(
                id = 2,
                title = "Burger",
                description = "7 cheesy pizza",
                amount = 235.60.toString(),
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            // act
            viewModel.addExpense(pizza).await()
            viewModel.addExpense(pizza1).await()

            val updatePizza = ExpenseData(
                id = 1,
                title = "Puf Pizza",
                description = "7 cheesy pizza",
                amount = 235.60.toString(),
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-01",
            )

            viewModel.addExpense(updatePizza).await()

            // assert
            fakeRepository.getAllExpenses().test {
                val expenses = awaitItem()
                val expense = expenses.find { it.id == 1 }
                assertThat(expense?.title).isEqualTo("Puf Pizza")
            }
        }
    }

}