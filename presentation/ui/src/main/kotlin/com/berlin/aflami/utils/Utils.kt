package com.berlin.aflami.utils

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