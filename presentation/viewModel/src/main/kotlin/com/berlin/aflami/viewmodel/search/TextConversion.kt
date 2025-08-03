package com.berlin.aflami.viewmodel.search

fun convertArabicToEnglish(input: String): String {
    val arabicDigits = "٠١٢٣٤٥٦٧٨٩".toCharArray()
    val englishDigits = "0123456789"

    return input.map { char ->
        val index = arabicDigits.indexOf(char)
        if (index != -1) englishDigits[index] else char
    }.joinToString("")
}