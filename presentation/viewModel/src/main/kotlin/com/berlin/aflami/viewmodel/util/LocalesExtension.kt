package com.berlin.aflami.viewmodel.util

import java.util.Locale

fun countryNameToIsoCode(countryName: String): String? {
    val locales = Locale.getAvailableLocales()
    return locales
        .filter { it.displayCountry.isNotEmpty() }
        .firstOrNull { it.getDisplayCountry(Locale.ENGLISH).equals(countryName, ignoreCase = true) }
        ?.country
}