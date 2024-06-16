package com.kanishthika.moneymatters.display.transaction.data

enum class TransactionType {
    CREDIT, DEBIT
}

fun stringToTransactionType(string: String): TransactionType {
    return if (string == "Credit") {
        TransactionType.CREDIT
    } else {
        TransactionType.DEBIT
    }
}