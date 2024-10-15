package com.kanishthika.moneymatters.display.account.data

import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDao: AccountDao) {
    val getAllAccount  = accountDao.getAllAccounts()

    suspend fun insertAccount(account: Account){
        accountDao.insertAccount(account)
    }
    suspend fun updateAccount(account: Account){
        accountDao.updateAccount(account)
    }

    suspend fun deleteAccount(account: Account){
        accountDao.deleteAccount(account)
    }

    suspend fun getAccountByName(name: String): Account {
        return accountDao.getAccountByName(name) ?: Account(0,"","",0.0)
    }
}