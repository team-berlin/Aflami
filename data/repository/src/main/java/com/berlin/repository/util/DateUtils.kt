package com.berlin.repository.util

import kotlinx.datetime.LocalDate

fun String.toLocalDate(): LocalDate {
    return if (this.isNotEmpty()) LocalDate.parse(this)
    else LocalDate(1960, 1, 1)
}