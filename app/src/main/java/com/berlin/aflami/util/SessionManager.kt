package com.berlin.aflami.util

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("tmdb_prefs", Context.MODE_PRIVATE)

    fun saveSessionId(sessionId: String) {
        prefs.edit().putString("SESSION_ID", sessionId).apply()
    }

    fun getSessionId(): String? = prefs.getString("SESSION_ID", null)
}