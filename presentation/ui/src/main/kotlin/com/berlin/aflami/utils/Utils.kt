package com.berlin.aflami.utils

import java.util.Locale

fun formatRating(rating: Double): String {
    return if (rating % 1 == 0.0) {
        rating.toInt().toString()
    } else {
        String.format(Locale.getDefault(), "%.1f", rating).trimEnd('0').trimEnd('.')
    }
}

fun Double.formatRatingForUi(): String {
    val rounded = String.format("%.1f", this)
    return if (rounded.endsWith(".0")) {
        rounded.dropLast(2)
    } else {
        rounded
    }
}

fun String.swapYearAndDay(): String {
    val parts = this.split("-")
    return if (parts.size == 3) {
        "${parts[2]}-${parts[1]}-${parts[0]}"
    } else this
}

fun String.formatDate(): String {
    val parts = this.split(" ")
    if (parts.size != 3) return this

    val year = parts[0]
    val month = parts[1]
    val day = parts[2]

    return "$day $month $year"
}