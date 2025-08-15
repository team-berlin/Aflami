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