package com.berlin.aflami.viewmodel.util


fun String.toEnglishDigits(): String {
    val arabicDigits = charArrayOf('٠','١','٢','٣','٤','٥','٦','٧','٨','٩')
    val englishDigits = charArrayOf('0','1','2','3','4','5','6','7','8','9')

    var result = this
    for (i in arabicDigits.indices) {
        result = result.replace(arabicDigits[i], englishDigits[i])
    }

    result = result.replace('٫', '.').replace('،', '.')

    return result
}

fun String.toDoubleSafe(): Double {
    return try {
        val normalized = this.toEnglishDigits()
        normalized.toDoubleOrNull() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}


