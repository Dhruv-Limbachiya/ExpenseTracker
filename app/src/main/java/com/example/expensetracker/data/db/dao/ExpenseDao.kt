package com.example.expensetracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.data.db.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): Flow<Expense>

    @Query("SELECT SUM(amount) FROM expense WHERE strftime('%m', date) = strftime('%m', 'now')")
    fun getExpensesOfCurrentMonth(): Flow<Double>

}
