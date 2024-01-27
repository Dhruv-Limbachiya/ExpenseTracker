package com.example.expensetracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("expense_tracker_test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context): ExpenseTrackerDB {
        return Room.inMemoryDatabaseBuilder(context, ExpenseTrackerDB::class.java)
            .allowMainThreadQueries().build()
    }
}