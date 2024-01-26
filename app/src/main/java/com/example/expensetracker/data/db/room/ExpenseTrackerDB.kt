package com.example.expensetracker.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetracker.data.db.dao.ExpenseDao
import com.example.expensetracker.data.db.entities.Expense

@Database(entities = [Expense::class], version = 1)
abstract class ExpenseTrackerDB : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao
    companion object {
        const val DB_NAME = "expense_tracker_db"
    }
}