package com.berlin.entity

data class User(
    val id: Int,
    val username: String,
    val name: String?,
    val avatarUrl: String?
)

enum class AppTheme {
    LIGHT, DARK,
}

enum class AppLanguage(val code: String) {
    EN("en"),
    AR("ar")
}