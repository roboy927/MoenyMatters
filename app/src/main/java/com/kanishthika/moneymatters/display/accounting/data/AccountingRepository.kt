package com.kanishthika.moneymatters.display.accounting.data

import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.BorrowerRepository
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseRepository
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.type.income.data.IncomeRepository
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.InsuranceRepository
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentRepository
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.LenderRepository
import com.kanishthika.moneymatters.display.accounting.type.loan.LoanRepository
import com.kanishthika.moneymatters.display.accounting.type.other.OtherRepository
import javax.inject.Inject

class AccountingRepository @Inject constructor(
    private val investmentRepository: InvestmentRepository,
    private val expenseRepository: ExpenseRepository,
    private val borrowerRepository: BorrowerRepository,
    private val lenderRepository: LenderRepository,
    private val incomeRepository: IncomeRepository,
    private val insuranceRepository: InsuranceRepository,
    private val otherRepository: OtherRepository,
    private val loanRepository: LoanRepository
){
    val getAllExpenses = expenseRepository.getAllItems()
    val getAllInvestments = investmentRepository.getAllItems()
    val getAllBorrowers = borrowerRepository.getAllItems()
    val getAllLenders = lenderRepository.getAllItems()
    val getAllIncomes = incomeRepository.getAllItems()
    val getAllInsurances = insuranceRepository.getAllItems()
 //   val getAllOthers = otherRepository.getAllOthers
  //  val getAllLoans = loanRepository.getAllLoans

    suspend fun updateExpense(expense: Expense){
        expenseRepository.updateItem(expense)
    }
    suspend fun updateInvestment(investment: Investment){
        investmentRepository.updateItem(investment)
    }
    suspend fun updateBorrower(borrower: Borrower){
        borrowerRepository.updateItem(borrower)
    }
    suspend fun updateLender(lender: Lender){
        lenderRepository.updateItem(lender)
    }

    suspend fun updateIncome(income: Income){
        incomeRepository.updateItem(income)
    }

    suspend fun updateInsurance(insurance: Insurance){
        insuranceRepository.updateItem(insurance)
    }

  /*  suspend fun updateOther(other: Other){
        otherRepository.updateItem(other)
    }

    suspend fun updateLoan(loan: Loan){
        loanRepository.updateItem(loan)
    }*/



}