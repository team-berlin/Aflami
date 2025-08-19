package com.berlin.aflami.viewmodel.util

import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

fun String.toDoubleSafe(): Double {
    return try {
        NumberFormat.getInstance(Locale.ENGLISH).parse(this)?.toDouble() ?: 0.0
    } catch (e: ParseException) {
        0.0
    }
}
