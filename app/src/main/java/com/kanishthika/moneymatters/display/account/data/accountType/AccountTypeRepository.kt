package com.kanishthika.moneymatters.display.account.data.accountType

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountTypeRepository @Inject constructor(
    private val accountTypeDao: AccountTypeDao
) {

    fun getAllAccountType(): Flow<List<AccountType>> {
        return accountTypeDao.getAllAccountType()
    }

    suspend fun addAccountType(accountType: AccountType): Long{
        return accountTypeDao.insertAccountType(accountType)
    }

    suspend fun deleteAccountType(accountType: AccountType){
        accountTypeDao.deleteAccountType(accountType)
    }
}