package com.berlin.aflami.viewmodel.util

import java.util.Locale

fun getCountriesNames(): List<String> {
    return countryNameToIsoMap.keys.sorted()
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

private val isoToCountryNameMap: Map<String, String> by lazy {
    countryNameToIsoMap.entries.associate { (name, iso) -> iso to name }
}