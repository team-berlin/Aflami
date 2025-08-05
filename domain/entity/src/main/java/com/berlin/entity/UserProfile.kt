package com.berlin.entity

data class UserProfile(
    val id: Int,
    val username: String,
    val name: String,
    val includeAdult: Boolean,
    val avatarUrl: String,
    val iso31661: String,
    val iso6391: String
)

enum class AppTheme {
    LIGHT, DARK,
}

enum class AppLanguage(val code: String) {
    EN("en"),
    AR("ar")
}