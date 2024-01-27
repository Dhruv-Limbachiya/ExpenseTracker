package com.example.expensetracker.data.db.dao

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.expensetracker.data.db.entities.Category
import com.example.expensetracker.data.db.entities.Expense
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
    fun testInsertExpense_whenInserted_shouldReturnTrue() {
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
            Log.i(TAG, "testInsertExpense_whenInserted_shouldReturnTrue: $rowId")
            // assert
            assertThat(rowId).isNotEqualTo(0)
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