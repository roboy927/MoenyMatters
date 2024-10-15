package com.kanishthika.moneymatters.display.account.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    // Query all accounts from the database
    @Query("SELECT * FROM accounts_List")
    fun getAllAccounts(): Flow<List<Account>>

    // Insert a new account into the database
    @Insert
    suspend fun insertAccount(account: Account)

    // Update an existing account in the database
    @Update
    suspend fun updateAccount(account: Account)

    // Delete an account from the database
    @Delete
    suspend fun deleteAccount (account: Account)

    @Query("SELECT * FROM accounts_List WHERE name = :accountName LIMIT 1")
    suspend fun getAccountByName(accountName: String): Account?
}