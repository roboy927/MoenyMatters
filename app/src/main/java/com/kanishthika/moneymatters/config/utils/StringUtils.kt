package com.kanishthika.moneymatters.config.utils

import java.text.DecimalFormat

fun capitalizeWords(sentence: String): String{
    return sentence.split(" ").joinToString(" "){ word ->
        word.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}

fun formatTo2Decimal(input: String): String {
    // Allow empty input
    if (input.isEmpty()) return ""

    val decimalFormat = DecimalFormat("0.00")
    val parsed = input.toDoubleOrNull()
    return if (parsed != null) {
        decimalFormat.format(parsed)
    } else {
        input
    }
}