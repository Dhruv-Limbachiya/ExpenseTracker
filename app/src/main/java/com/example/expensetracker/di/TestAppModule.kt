package com.example.expensetracker.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.example.expensetracker.data.repositories.ExpenseRepository
import com.example.expensetracker.domain.repositories.FakeExpenseRepository
import com.example.expensetracker.domain.usecases.AddOrUpdateExpense
import com.example.expensetracker.domain.usecases.GetCurrentMonthTotalSpent
import com.example.expensetracker.domain.usecases.GetExpense
import com.example.expensetracker.domain.usecases.GetExpenses
import com.example.expensetracker.domain.usecases.RemoveExpense
import com.example.expensetracker.domain.usecases.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("expense_tracker_test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context): ExpenseTrackerDB {
        return Room.inMemoryDatabaseBuilder(context, ExpenseTrackerDB::class.java)
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    @Named("fake_expense_tracker_repository")
    fun provideFakeRepository() : ExpenseRepository {
        return FakeExpenseRepository()
    }

    @Singleton
    @Provides
    @Named("test_use_case")
    fun provideTestUseCase(@Named("fake_expense_tracker_repository") repository: ExpenseRepository) : UseCase {
        Log.i("TestModule", "provideTestUseCase: REPO : $repository")
        return UseCase(
            getExpenses = GetExpenses(repository),
            addOrUpdateExpense = AddOrUpdateExpense(repository),
            removeExpense = RemoveExpense(repository),
            getCurrentMonthTotalSpent = GetCurrentMonthTotalSpent(repository),
            getExpense = GetExpense(repository),
        )
    }
}