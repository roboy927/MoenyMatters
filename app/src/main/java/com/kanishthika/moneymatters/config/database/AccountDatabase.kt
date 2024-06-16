package com.kanishthika.moneymatters.config.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanishthika.moneymatters.display.account.data.Account
import com.kanishthika.moneymatters.display.account.data.AccountDao
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseDao
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentDao
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.BorrowerDao
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.LenderDao
import com.kanishthika.moneymatters.display.transaction.data.Transaction
import com.kanishthika.moneymatters.display.transaction.data.TransactionDao


@Database(entities = [
    Account::class,
    Transaction::class,
    Investment::class,
    Expense::class,
    Borrower::class,
    Lender::class ], version = 1, exportSchema = false)
abstract class AccountDatabase: RoomDatabase() {
    // Get the  DAO
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun investmentDao() : InvestmentDao
    abstract fun expenseDao() : ExpenseDao
    abstract fun borrowerDao(): BorrowerDao
    abstract fun lenderDao(): LenderDao

}