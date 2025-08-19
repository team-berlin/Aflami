package com.berlin.aflami.viewmodel.search

fun parseRating(rate: String): String {
    val arabicDigits = "٠١٢٣٤٥٦٧٨٩"
    val englishDigits = "0123456789"

    return rate.map { char ->
        val index = arabicDigits.indexOf(char)
        if (index != -1) englishDigits[index] else char
    }.joinToString("")
}

