package com.kanishthika.moneymatters.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanishthika.moneymatters.account.data.Account
import com.kanishthika.moneymatters.account.data.AccountDao
import com.kanishthika.moneymatters.accounting.expense.data.Expense
import com.kanishthika.moneymatters.accounting.expense.data.ExpenseDao
import com.kanishthika.moneymatters.accounting.investments.data.Investment
import com.kanishthika.moneymatters.accounting.investments.data.InvestmentDao
import com.kanishthika.moneymatters.transaction.data.model.Transaction
import com.kanishthika.moneymatters.transaction.data.model.TransactionDao


@Database(entities = [
    Account::class,
    Transaction::class,
    Investment::class, Expense::class], version = 1, exportSchema = false)
abstract class AccountDatabase: RoomDatabase() {
    // Get the  DAO
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun investmentDao() : InvestmentDao
    abstract fun expenseDao() : ExpenseDao

}