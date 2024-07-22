package com.kanishthika.moneymatters.display.transaction.ui.addTransaction.element

enum class SplitOptions{
    None, TWO, THREE, FOUR
}

enum class DivideOptions {
    EQUAL, CUSTOM
}

fun getSplitPageCount(splitOptions: SplitOptions): Int{
    return when(splitOptions){
        SplitOptions.None -> 1
        SplitOptions.TWO -> 2
        SplitOptions.THREE -> 3
        SplitOptions.FOUR -> 4
    }
}