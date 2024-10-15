package com.kanishthika.moneymatters.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountDao
import com.kanishthika.moneymatters.display.account.data.accountType.AccountType
import com.kanishthika.moneymatters.display.account.data.accountType.AccountTypeDao
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.BorrowerDao
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseDao
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.type.income.data.IncomeDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.InsuranceDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceType
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeDao
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentDao
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.LenderDao
import com.kanishthika.moneymatters.display.label.data.Label
import com.kanishthika.moneymatters.display.label.data.LabelDao
import com.kanishthika.moneymatters.display.label.data.labelType.LabelType
import com.kanishthika.moneymatters.display.label.data.labelType.LabelTypeDao
import com.kanishthika.moneymatters.display.reminder.data.MMReminder
import com.kanishthika.moneymatters.display.reminder.data.MMReminderDao
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionDao


@Database(
    entities = [
        Account::class,
        AccountType::class,
        Transaction::class,
        Investment::class,
        Expense::class,
        Borrower::class,
        Lender::class,
        Income::class,
        Insurance::class,
        InsuranceType::class,
        LabelType::class,
        Label::class,
        MMReminder::class
    ], version = 1, exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {
    // Get the  DAO
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun borrowerDao(): BorrowerDao
    abstract fun lenderDao(): LenderDao
    abstract fun incomeDao(): IncomeDao
    abstract fun insuranceDao(): InsuranceDao
    abstract fun insuranceTypeDao(): InsuranceTypeDao
    abstract fun accountTypeDao(): AccountTypeDao
    abstract fun mmReminderDao(): MMReminderDao
    abstract fun labelDao(): LabelDao
    abstract fun labelTypeDao(): LabelTypeDao

}