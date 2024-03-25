package com.kanishthika.moneymatters.di

import android.content.Context
import androidx.room.Room
import com.kanishthika.moneymatters.account.data.AccountDao
import com.kanishthika.moneymatters.accounting.expense.data.ExpenseDao
import com.kanishthika.moneymatters.accounting.investments.data.InvestmentDao
import com.kanishthika.moneymatters.data.database.AccountDatabase
import com.kanishthika.moneymatters.transaction.data.model.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun provideAccountDB(@ApplicationContext context: Context) : AccountDatabase =
        Room.databaseBuilder(context, AccountDatabase::class.java, "AccountDB").build()

    @Provides
    fun provideAccountDao(accountDatabase: AccountDatabase): AccountDao = accountDatabase.accountDao()

    @Provides
    fun provideTransactionDao(accountDatabase: AccountDatabase) : TransactionDao = accountDatabase.transactionDao()

    @Provides
    fun provideInvestmentDao(accountDatabase: AccountDatabase) : InvestmentDao = accountDatabase.investmentDao()

    @Provides
    fun provideExpenseDao(accountDatabase: AccountDatabase) : ExpenseDao = accountDatabase.expenseDao()

}