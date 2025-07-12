package com.berlin.repository.util

import kotlinx.datetime.LocalDate

fun String.toLocalDate(): LocalDate {
    return try {
       LocalDate.parse(this)
    }
    catch(e: Exception) {
        LocalDate(1960, 1, 1)
   }
}