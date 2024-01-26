package com.example.expensetracker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val amount: Double,
    @ColumnInfo(name = "column_id")
    val categoryId: Int,
    val date: String
)
