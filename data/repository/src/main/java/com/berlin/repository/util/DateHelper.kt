package com.berlin.repository.util

import kotlinx.datetime.LocalDate

fun stringToLocalDate(dateString: String): LocalDate {
    return runCatching {
        LocalDate.parse(dateString)
    }.getOrElse { LocalDate.parse("1960-01-01") }
}

fun Int?.formatDuration(): String? {
    if (this == null || this == 0) return null
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${remainingMinutes}m"
}