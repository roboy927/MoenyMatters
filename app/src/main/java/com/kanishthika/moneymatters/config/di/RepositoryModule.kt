package com.kanishthika.moneymatters.config.di

import com.kanishthika.moneymatters.display.account.data.accountType.AccountTypeDao
import com.kanishthika.moneymatters.display.account.data.accountType.AccountTypeRepository
import com.kanishthika.moneymatters.display.accounting.data.FinancialRepository
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.Borrower
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.BorrowerDao
import com.kanishthika.moneymatters.display.accounting.type.borrower.data.BorrowerRepository
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.Expense
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseDao
import com.kanishthika.moneymatters.display.accounting.type.expenses.data.ExpenseRepository
import com.kanishthika.moneymatters.display.accounting.type.income.data.Income
import com.kanishthika.moneymatters.display.accounting.type.income.data.IncomeDao
import com.kanishthika.moneymatters.display.accounting.type.income.data.IncomeRepository
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.Insurance
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.InsuranceDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.InsuranceRepository
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeDao
import com.kanishthika.moneymatters.display.accounting.type.insurance.data.insuranceType.InsuranceTypeRepository
import com.kanishthika.moneymatters.display.accounting.type.investments.data.Investment
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentDao
import com.kanishthika.moneymatters.display.accounting.type.investments.data.InvestmentRepository
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.Lender
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.LenderDao
import com.kanishthika.moneymatters.display.accounting.type.lenders.data.LenderRepository
import com.kanishthika.moneymatters.display.accounting.type.loan.LoanRepository
import com.kanishthika.moneymatters.display.accounting.type.other.OtherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao): FinancialRepository<Expense> {
        return ExpenseRepository(expenseDao) // Assuming you have an implementation of FinancialRepository
    }

    @Provides
    @Singleton
    fun provideInvestmentRepository(investmentDao: InvestmentDao): FinancialRepository<Investment> {
        return InvestmentRepository(investmentDao)
    }

    @Provides
    @Singleton
    fun provideIncomeRepository(incomeDao: IncomeDao): FinancialRepository<Income> {
        return IncomeRepository(incomeDao)
    }

    @Provides
    @Singleton
    fun provideLenderRepository(lenderDao: LenderDao): FinancialRepository<Lender> {
        return LenderRepository(lenderDao)
    }

    @Provides
    @Singleton
    fun provideBorrowerRepository(borrowerDao: BorrowerDao): FinancialRepository<Borrower> {
        return BorrowerRepository(borrowerDao)
    }

    @Provides
    @Singleton
    fun provideInsuranceRepository(insuranceDao: InsuranceDao): FinancialRepository<Insurance> {
        return InsuranceRepository(insuranceDao)
    }

    @Provides
    @Singleton
    fun provideInsuranceTypeRepository(insuranceTypeDao: InsuranceTypeDao): InsuranceTypeRepository {
        return InsuranceTypeRepository(insuranceTypeDao)
    }

    @Provides
    @Singleton
    fun provideAccountTypeRepository(accountTypeDao: AccountTypeDao): AccountTypeRepository {
        return AccountTypeRepository(accountTypeDao)
    }

    @Provides
    @Singleton
    fun provideOtherRepository(): OtherRepository {
        return OtherRepository()
    }

    @Provides
    @Singleton
    fun provideLoanRepository(): LoanRepository {
        return LoanRepository()
    }

}
