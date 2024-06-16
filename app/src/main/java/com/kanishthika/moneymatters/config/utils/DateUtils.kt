package com.kanishthika.moneymatters.config.utils

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toEpochMillis(
    zoneId: ZoneId = ZoneId.systemDefault(),
): Long {
    return this.atStartOfDay()
        .atZone(zoneId)
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.toEpochMillisUTC(): Long = this.toEpochMillis(ZoneId.of("UTC"))

fun LocalDate.toUIString(): String {
    return DateTimeFormatter.ofPattern("dd MMMM yyyy").format(this)
}



fun convertToLocalDate(dateString: String, pattern: String = "dd/MM/yyyy"): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(dateString, dateFormatter)
}

fun createMonthListForYear(year: Int): List<String> {
    val monthList = mutableListOf<String>()
    for (month in 1..12) {
        val yearMonth = YearMonth.of(year, month)
        val monthName = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        monthList.add("$monthName $year")
    }
    return monthList
}