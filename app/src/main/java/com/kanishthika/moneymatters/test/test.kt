package com.kanishthika.moneymatters.test

import com.kanishthika.moneymatters.config.utils.localDateTimeToTimeStamp
import com.kanishthika.moneymatters.config.utils.separateDateFromTimeStamp
import com.kanishthika.moneymatters.config.utils.toEpochMillis
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun main() {
    val startOfMonth = (LocalDate.now().withDayOfMonth(1)).toEpochMillis()
    println("Start of the month: ${separateDateFromTimeStamp(startOfMonth)}")

    val endOfMonth: LocalDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
    println("End of the month: $endOfMonth")


    println("${ZoneId.systemDefault()}")

    val startOfDay = localDateTimeToTimeStamp(LocalDateTime.now().toLocalDate().atStartOfDay())
    val endOfDay = localDateTimeToTimeStamp(LocalDate.now().atTime(LocalTime.MAX))

    println("$startOfDay , $endOfDay")
}
