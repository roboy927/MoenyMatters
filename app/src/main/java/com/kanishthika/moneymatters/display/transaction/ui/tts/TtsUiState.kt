package com.kanishthika.moneymatters.display.transaction.ui.tts

import com.kanishthika.moneymatters.config.utils.toUIString
import com.kanishthika.moneymatters.display.account.data.Account
import java.time.LocalDate

data class TtsUiState(
    val date: String = LocalDate.now().toUIString(),
    val dbAccountName: Account = Account(0,"","",0.0),
    val crAccountName: Account = Account(0,"","",0.0),
    val accountList: List<Account> = emptyList(),
    val amount: String = "",
    val description: String = "",
)
