package com.dhruvv.expensetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhruvv.expensetracker.data.db.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

    @Delete
    suspend fun deleteExpense(expense: Expense): Int

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense where id = :expenseId")
    suspend fun getExpenseById(expenseId: Int): Expense?

    @Query("SELECT SUM(amount) FROM expense WHERE strftime('%m', date) = strftime('%m', 'now')")
    fun getExpensesOfCurrentMonth(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM expense WHERE strftime('%m', date) = :month")
    fun getExpensesByMonth(month: String): Flow<Double?>
}
