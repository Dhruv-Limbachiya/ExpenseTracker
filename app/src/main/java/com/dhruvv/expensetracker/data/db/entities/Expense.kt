package com.dhruvv.expensetracker.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val amount: Double = 0.0,
    @ColumnInfo(name = "column_id")
    val categoryId: Int = 0,
    val date: String? = null,
)
