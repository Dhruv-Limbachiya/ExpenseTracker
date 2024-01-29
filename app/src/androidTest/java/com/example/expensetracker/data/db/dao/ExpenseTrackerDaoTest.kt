package com.example.expensetracker.data.db.dao

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.expensetracker.data.db.entities.Category
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class ExpenseTrackerDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this) // used to inject dependencies

    //swaps the background executor used by the Architecture Components with a different one that executes each task synchronously.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("expense_tracker_test_db")
    lateinit var expenseTrackerDB: ExpenseTrackerDB

    lateinit var expenseDao: ExpenseDao
    @Before
    fun setUp() {
        hiltRule.inject()
        expenseDao = expenseTrackerDB.getExpenseDao()
        Log.i(TAG, "setUp: expense dao : $expenseDao ")
    }

    @Test
    fun testInsertExpense_whenFetch_shouldReturnCorrectExpenseRecord() {
        runBlocking {
            // arrange
            val expense = Expense(
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2022-01-01",
            )
            // act
            val rowId = expenseDao.insertExpense(expense)

            val insertedExpense = expenseDao.getAllExpenses().first()[0]
            // assert
            assertThat(rowId).isNotEqualTo(0) //inserted properly
            assertThat(insertedExpense.title).isEqualTo("Pizza")
        }
    }

    @Test
    fun testExpenses_whenFetched_shouldReturnAllExpenses() {
        runBlocking {
            // arrange
            val pizza = Expense(
                title = "Pizza",
                description = "7 cheesy pizza",
                amount = 235.60,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2022-01-01",
            )

            val burger = Expense(
                title = "Burger",
                description = "Veg Double Patty Burger",
                amount = 199.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2022-01-02",
            )

            val shirt = Expense(
                title = "Shirt",
                description = "Cotton Shirt XL",
                amount = 799.0,
                categoryId = Category.CLOTHING_CATEGORY.categoryId,
                date = "2022-01-02",
            )

            expenseDao.insertExpense(pizza)

            launch {
                delay(500)
                expenseDao.insertExpense(burger)

                delay(1000)
                expenseDao.insertExpense(shirt)
            }

            // act & assert
            expenseDao.getAllExpenses().test {
                val firstExpenseList = this.awaitItem()
                assertThat(firstExpenseList.first().title).isEqualTo("Pizza")
                val secondExpenseList = this.awaitItem()
                assertThat(secondExpenseList[1].title).isEqualTo("Burger")
                val thirdExpenseList = this.awaitItem()
                assertThat(thirdExpenseList[2].title).isEqualTo("Shirt")
                cancel()
            }
        }
    }

    @Test
    fun testGetExpenses_whenExpenseTableIsEmpty_shouldReturnEmptyList() {
        // act
        runBlocking {
            val expenses = expenseDao.getAllExpenses().first()
            // assert
            assertThat(expenses).isEmpty()
        }
    }

    @Test
    fun testCurrentMonthExpenses_shouldReturnCorrectAmount() {
       runBlocking {
           // arrange
           val pizza = Expense(
               title = "Pizza",
               description = "7 cheesy pizza",
               amount = 235.60,
               categoryId = Category.FOOD_CATEGORY.categoryId,
               date = "2024-01-01",
           )

           val burger = Expense(
               title = "Burger",
               description = "Veg Double Patty Burger",
               amount = 199.0,
               categoryId = Category.FOOD_CATEGORY.categoryId,
               date = "2024-01-01",
           )

           val shirt = Expense(
               title = "Shirt",
               description = "Cotton Shirt XL",
               amount = 799.0,
               categoryId = Category.CLOTHING_CATEGORY.categoryId,
               date = "2024-02-02",
           )

           expenseDao.insertExpense(pizza)
           expenseDao.insertExpense(burger)
           expenseDao.insertExpense(shirt)

            expenseDao.getAllExpenses().test {
                val expense =  this.awaitItem()
               assertThat(expense.size).isEqualTo(3)
           }


           // act
           val totalExpense = expenseDao.getExpensesOfCurrentMonth().first()

           //assert
           assertThat(totalExpense).isEqualTo(434.6)
       }
    }

    @Test
    fun testMarchMonthExpenses_shouldReturnCorrectAmount() {
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
                description = "Veg Double Patty Burger",
                amount = 199.0,
                categoryId = Category.FOOD_CATEGORY.categoryId,
                date = "2024-03-02",
            )

            val shirt = Expense(
                title = "Shirt",
                description = "Cotton Shirt XL",
                amount = 799.0,
                categoryId = Category.CLOTHING_CATEGORY.categoryId,
                date = "2024-03-04",
            )

            expenseDao.insertExpense(pizza)
            expenseDao.insertExpense(burger)
            expenseDao.insertExpense(shirt)

            expenseDao.getAllExpenses().test {
                val expense =  this.awaitItem()
                assertThat(expense.size).isEqualTo(3)
            }


            // act
            val totalExpense = expenseDao.getExpensesByMonth("03").first()

            //assert
            assertThat(totalExpense).isEqualTo(1233.6)
        }
    }

    @Test
    fun testCurrentMonthExpenses_whenThereIsNoExpenseRecorded_shouldReturnNull() {
        runBlocking {
            // act
            val totalExpense = expenseDao.getExpensesOfCurrentMonth().first()

            //assert
            assertThat(totalExpense).isNull()
        }
    }

    @After
    fun tearDown() {
        expenseTrackerDB.close()
    }
    
    companion object {
        private const val TAG = "ExpenseTrackerDaoTest"
    }
}