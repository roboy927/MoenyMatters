package com.kanishthika.moneymatters.account.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kanishthika.moneymatters.account.data.Account
import com.kanishthika.moneymatters.account.data.AccountDao
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDao: AccountDao) {
    val getAllAccount: LiveData<List<Account>>  = accountDao.getAllAccounts().asLiveData()

    suspend fun insertAccount(account: Account){
        accountDao.insertAccount(account)
    }

    suspend fun deleteAccount(account: Account){
        accountDao.deleteAccount(account)
    }
}