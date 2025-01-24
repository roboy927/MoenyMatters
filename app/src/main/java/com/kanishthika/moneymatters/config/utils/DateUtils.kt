package com.kanishthika.moneymatters.config.utils

import android.icu.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

fun LocalDate.toEpochMillisUTC(): Long = this.toEpochMillis()

fun LocalDate.toUIString(): String {
    return DateTimeFormatter.ofPattern("dd MMMM yyyy").format(this)
}

fun convertToLocalDate(dateString: String, pattern: String = "dd/MM/yyyy"): LocalDate {
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(dateString, dateFormatter)
}

fun localDateTimeToTimeStamp(localDateTime: LocalDateTime): Long{
    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    return instant.toEpochMilli()
}

fun convertStringsToTimeStamp(date: String, time: String, datePattern: String = "dd MMMM yyyy", timePattern: String = "HH : mm") : Long {
    val localDateTime = LocalDateTime.of(
        convertToLocalDate(date, datePattern),
        time.convertToLocalTime(timePattern)
    )
    return localDateTimeToTimeStamp(localDateTime)
}

fun String.convertToLocalTime(pattern: String): LocalTime = LocalTime.parse(this, DateTimeFormatter.ofPattern(pattern))

fun separateDateFromTimeStamp(timeStamp: Long): String{
    val instant = Instant.ofEpochMilli(timeStamp)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return localDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
}

fun separateTimeFromTimeStamp(timeStamp: Long): String{
    val instant = Instant.ofEpochMilli(timeStamp)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return localDateTime.format(DateTimeFormatter.ofPattern("HH : mm"))
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

fun convertDateToYearString(inputDate: String, inputFormatPattern: String): String {
    // Define the input and output date formats
    val inputFormat = SimpleDateFormat(inputFormatPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())

    // Parse the input date
    val date = inputFormat.parse(inputDate)

    // Format the date to the desired output format
    return outputFormat.format(date)
}
