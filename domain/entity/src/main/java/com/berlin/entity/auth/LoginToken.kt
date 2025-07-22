package com.berlin.entity.auth

data class LoginToken(
    val success: Boolean,
    val expiresAt: String,
    val requestToken: String,
    )
