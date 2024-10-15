package com.kanishthika.moneymatters.display.label.ui

import com.kanishthika.moneymatters.display.label.data.labelType.LabelType

data class AddLabelUiState(
    val labelName: String = "",
    val labelTypeList: List<LabelType> = emptyList(),
    val selectedLabelType: LabelType = LabelType(0,""),
    val initialAmount: String = "",
    val additional: String = ""
)
