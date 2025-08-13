package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface UserProfileLocalDataSource {
    fun observe(): Flow<UserProfileEntity?>
    suspend fun get(): UserProfileEntity?
    suspend fun upsert(entity: UserProfileEntity)
    suspend fun clear()
}