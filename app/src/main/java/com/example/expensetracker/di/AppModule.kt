package com.example.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.db.room.ExpenseTrackerDB
import com.example.expensetracker.data.repositories.ExpenseRepository
import com.example.expensetracker.domain.repositories.FakeExpenseRepository
import com.example.expensetracker.domain.repositories.IExpenseRepository
import com.example.expensetracker.domain.usecases.AddOrUpdateExpense
import com.example.expensetracker.domain.usecases.GetCurrentMonthTotalSpent
import com.example.expensetracker.domain.usecases.GetExpenses
import com.example.expensetracker.domain.usecases.RemoveExpense
import com.example.expensetracker.domain.usecases.UserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("expense_tracker_db")
    fun provideExpenseTrackerDB(@ApplicationContext context: Context): ExpenseTrackerDB {
        return Room.databaseBuilder(context, ExpenseTrackerDB::class.java,ExpenseTrackerDB.DB_NAME).build()
    }

    @Singleton
    @Provides
    @Named("expense_tracker_repository")
    fun provideExpenseTrackerRepository() : ExpenseRepository {
        return IExpenseRepository()
    }

    @Singleton
    @Provides
    @Named("main_use_case")
    fun provideUseCase(@Named("expense_tracker_repository") repository: ExpenseRepository) : UserCase {
        return UserCase(
            getExpense = GetExpenses(repository),
            addOrUpdateExpense = AddOrUpdateExpense(repository),
            removeExpense = RemoveExpense(repository),
            getCurrentMonthTotalSpent = GetCurrentMonthTotalSpent(repository),
        )
    }
}