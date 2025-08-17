package com.berlin.aflami.viewmodel.util

import java.util.Locale

fun getCountriesNames(): List<String> {
    val currentLocale = Locale.getDefault()
    return Locale.getISOCountries().map { iso ->
        Locale(currentLocale.language, iso).getDisplayCountry(currentLocale)
    }.sorted()
}

fun getCountryIsoCode(countryName: String): String? {
    return countryNameToIsoMap[countryName]
}

private val countryNameToIsoMap: Map<String, String> by lazy {
    Locale.getISOCountries().associateBy(
        keySelector = { Locale("", it).displayCountry },
        valueTransform = { it }
    )
}