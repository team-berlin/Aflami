package com.berlin.local.datasource

import com.berlin.local.dao.UserPointsDao
import com.berlin.repository.datasource.local.datasource.GameLocalDataSource
import com.berlin.repository.datasource.local.dto.UserPointsEntity
import javax.inject.Inject

class GameLocalDataSourceImpl @Inject constructor(
    private val userPointsDao: UserPointsDao
) : GameLocalDataSource {
    override suspend fun getPoints(userID: Int): Int {
        return userPointsDao.getPoints(userID) ?: 0
    }

    override suspend fun updatePoints(userID: Int, points: Int) {
        userPointsDao.upsertPoints(UserPointsEntity(userID, points))
    }

    override suspend fun addPoints(userID: Int, points: Int) {
        userPointsDao.upsertPoints(UserPointsEntity(userID, points))
    }
}