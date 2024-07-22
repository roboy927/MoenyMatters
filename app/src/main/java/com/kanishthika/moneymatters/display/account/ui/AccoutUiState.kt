package com.kanishthika.moneymatters.display.account.ui

import com.kanishthika.moneymatters.display.account.data.accountType.AccountType

data class AccountUiState (
    val name: String = "",
    val type: AccountType = AccountType(0, ""),
    val amount: String =""
)