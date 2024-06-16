package com.kanishthika.moneymatters.config.utils

fun capitalizeWords(sentence: String): String{
    return sentence.split(" ").joinToString(" "){ word ->
        word.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}