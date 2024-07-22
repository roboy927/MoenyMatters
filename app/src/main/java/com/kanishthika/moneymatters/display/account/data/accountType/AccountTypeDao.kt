package com.kanishthika.moneymatters.display.account.data.accountType

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountTypeDao {
    @Query("SELECT * FROM account_type_list")
    fun getAllAccountType(): Flow<List<AccountType>>

    @Insert
    suspend fun insertAccountType(accountType: AccountType) : Long

    @Delete
    suspend fun deleteAccountType (accountType: AccountType)
}