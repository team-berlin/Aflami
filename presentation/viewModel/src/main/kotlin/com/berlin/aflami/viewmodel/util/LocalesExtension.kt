package com.berlin.aflami.viewmodel.util

import android.content.Context
import com.berlin.aflami.viewmodel.search_world_tour.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

fun countryNameToIsoCode(countryName: String): String? {
    val locales = Locale.getAvailableLocales()
    return locales
        .filter { it.displayCountry.isNotEmpty() }
        .firstOrNull { it.getDisplayCountry(Locale.ENGLISH).equals(countryName, ignoreCase = true) }
        ?.country
}

fun Context.loadCountriesFromAssets(): List<Country> {
    val json = assets.open("countries.json").bufferedReader().use { it.readText() }
    return Gson().fromJson(json, object : TypeToken<List<Country>>() {}.type)
}
