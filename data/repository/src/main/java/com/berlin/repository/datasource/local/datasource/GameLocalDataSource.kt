package com.berlin.repository.datasource.local.datasource

interface GameLocalDataSource {
    suspend fun getPoints(userID: Int): Int
    suspend fun updatePoints(userID: Int, points: Int)
    suspend fun addPoints(userID: Int, points: Int)
}