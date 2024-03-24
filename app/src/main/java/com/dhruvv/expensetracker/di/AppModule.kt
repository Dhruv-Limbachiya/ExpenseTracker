package com.dhruvv.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.dhruvv.expensetracker.data.db.room.ExpenseTrackerDB
import com.dhruvv.expensetracker.data.repositories.ExpenseRepository
import com.dhruvv.expensetracker.domain.repositories.IExpenseRepository
import com.dhruvv.expensetracker.domain.usecases.AddOrUpdateExpense
import com.dhruvv.expensetracker.domain.usecases.GetCurrentMonthTotalSpent
import com.dhruvv.expensetracker.domain.usecases.GetExpense
import com.dhruvv.expensetracker.domain.usecases.GetExpenses
import com.dhruvv.expensetracker.domain.usecases.RemoveExpense
import com.dhruvv.expensetracker.domain.usecases.UseCase
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
    fun provideExpenseTrackerRepository(@Named("expense_tracker_db") expenseTrackerDB: ExpenseTrackerDB) : ExpenseRepository {
        return IExpenseRepository(expenseTrackerDB)
    }

    @Singleton
    @Provides
    @Named("main_use_case")
    fun provideUseCase(@Named("expense_tracker_repository") repository: ExpenseRepository) : UseCase {
        return UseCase(
            getExpenses = GetExpenses(repository),
            addOrUpdateExpense = AddOrUpdateExpense(repository),
            removeExpense = RemoveExpense(repository),
            getCurrentMonthTotalSpent = GetCurrentMonthTotalSpent(repository),
            getExpense = GetExpense(repository),
        )
    }
}