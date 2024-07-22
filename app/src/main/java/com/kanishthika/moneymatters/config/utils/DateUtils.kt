package com.kanishthika.moneymatters.config.utils

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

fun convertDateToMonthYearString(inputDate: String, inputFormatPattern: String): String {
    // Define the input and output date formats
    val inputFormat = SimpleDateFormat(inputFormatPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    // Parse the input date
    val date = inputFormat.parse(inputDate)

    // Format the date to the desired output format
    return outputFormat.format(date)
}
