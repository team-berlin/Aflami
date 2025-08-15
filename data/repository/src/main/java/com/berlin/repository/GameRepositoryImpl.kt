package com.berlin.repository

import com.berlin.repository.datasource.local.GameLocalDataSource
import repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameLocalDataSource: GameLocalDataSource
): GameRepository {
    override suspend fun getPoints(userID: Int): Int {
        return gameLocalDataSource.getPoints(userID)
    }

    override suspend fun updatePoints(userID: Int, points: Int) {
        gameLocalDataSource.updatePoints(userID, points)
    }

    override suspend fun addPoints(userID: Int, points: Int) {
        gameLocalDataSource.addPoints(userID, points)
    }
}