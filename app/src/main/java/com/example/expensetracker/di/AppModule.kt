package com.example.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Named("expense_tracker_db")
    fun provideExpenseTrackerDB(@ApplicationContext context: Context): ExpenseTrackerDB {
        return Room.databaseBuilder(context, ExpenseTrackerDB::class.java,ExpenseTrackerDB.DB_NAME).build()
    }
}