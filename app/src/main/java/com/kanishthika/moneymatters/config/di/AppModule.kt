package com.kanishthika.moneymatters.config.di

import android.content.Context
import androidx.room.Room
import com.kanishthika.moneymatters.config.database.AccountDatabase
import com.kanishthika.moneymatters.display.account.data.AccountDao
import com.kanishthika.moneymatters.display.account.data.accountType.AccountTypeDao
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.BorrowerDao
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseDao
import com.kanishthika.moneymatters.display.accounting.type.income.data.IncomeDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.InsuranceDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeDao
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentDao
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.LenderDao
import com.kanishthika.moneymatters.display.label.data.LabelDao
import com.kanishthika.moneymatters.display.label.data.labelType.LabelTypeDao
import com.kanishthika.moneymatters.display.reminder.data.MMReminderDao
import com.kanishthika.moneymatters.display.transaction.data.TransactionDao
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
        Room.databaseBuilder(context, AccountDatabase::class.java, "AccountDB")
        .createFromAsset("database/defaultMM.db")
            .build()

    @Provides
    fun provideAccountDao(accountDatabase: AccountDatabase): AccountDao = accountDatabase.accountDao()

    @Provides
    fun provideTransactionDao(accountDatabase: AccountDatabase) : TransactionDao = accountDatabase.transactionDao()

    @Provides
    fun provideInvestmentDao(accountDatabase: AccountDatabase) : InvestmentDao = accountDatabase.investmentDao()

    @Provides
    fun provideExpenseDao(accountDatabase: AccountDatabase) : ExpenseDao = accountDatabase.expenseDao()

    @Provides
    fun provideBorrowerDa0(accountDatabase: AccountDatabase) : BorrowerDao = accountDatabase.borrowerDao()

    @Provides
    fun provideLenderDao(accountDatabase: AccountDatabase) : LenderDao = accountDatabase.lenderDao()

    @Provides
    fun provideIncomeDao(accountDatabase: AccountDatabase) : IncomeDao = accountDatabase.incomeDao()

    @Provides
    fun provideInsuranceDao(accountDatabase: AccountDatabase) : InsuranceDao = accountDatabase.insuranceDao()

    @Provides
    fun provideInsuranceTypeDao(accountDatabase: AccountDatabase): InsuranceTypeDao = accountDatabase.insuranceTypeDao()

    @Provides
    fun provideAccountTypeDao(accountDatabase: AccountDatabase): AccountTypeDao = accountDatabase.accountTypeDao()

    @Provides
    fun provideMMReminderDao(accountDatabase: AccountDatabase): MMReminderDao = accountDatabase.mmReminderDao()

    @Provides
    fun provideLabelDao(accountDatabase: AccountDatabase): LabelDao = accountDatabase.labelDao()

    @Provides
    fun provideLabelTypeDao(accountDatabase: AccountDatabase): LabelTypeDao = accountDatabase.labelTypeDao()


}
