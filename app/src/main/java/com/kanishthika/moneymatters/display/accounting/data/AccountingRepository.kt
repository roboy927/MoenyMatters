package com.kanishthika.moneymatters.display.accounting.data

import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseRepository
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentRepository
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.lb.borrower.data.BorrowerRepository
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lb.lenders.data.LenderRepository
import javax.inject.Inject

class AccountingRepository @Inject constructor(
    private val investmentRepository: InvestmentRepository,
    private val expenseRepository: ExpenseRepository,
    private val borrowerRepository: BorrowerRepository,
    private val lenderRepository: LenderRepository
){
    val getAllExpenses = expenseRepository.getAllExpenses
    val getAllInvestments = investmentRepository.getAllInvestments
    val getAllBorrowers = borrowerRepository.getAllBorrowers
    val getAllLenders = lenderRepository.getAllLenders

    suspend fun updateExpense(expense: Expense){
        expenseRepository.updateExpense(expense)
    }
    suspend fun updateInvestment(investment: Investment){
        investmentRepository.updateInvestment(investment)
    }
    suspend fun updateBorrower(borrower: Borrower){
        borrowerRepository.updateBorrower(borrower)
    }
    suspend fun updateLender(lender: Lender){
        lenderRepository.updateLender(lender)
    }



}