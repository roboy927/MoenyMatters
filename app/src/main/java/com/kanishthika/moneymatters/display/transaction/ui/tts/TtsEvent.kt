package com.kanishthika.moneymatters.display.transaction.ui.tts

import com.kanishthika.moneymatters.display.account.data.Account

interface TtsEvent{
    data class SetDate(val date: String): TtsEvent
    data class SetDbAccount(val dbAccount: Account): TtsEvent
    data class SetCrAccount(val crAccount: Account): TtsEvent
    data class SetAmount(val amount: String): TtsEvent
    data class SetDescription(val description: String): TtsEvent
    object AddTransaction: TtsEvent
}