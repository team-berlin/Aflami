package com.berlin.repository

import com.berlin.entity.UserProfile
import com.berlin.repository.datasource.local.UserProfileLocalDataSource
import com.berlin.repository.datasource.remote.UserRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userProfileLocalDataSource: UserProfileLocalDataSource
) : UserProfileRepository {
    override suspend fun getUserProfile(): UserProfile {
        val dto = userRemoteDataSource.getUserProfile()
        val entity = dto.toEntity(now = System.currentTimeMillis())
        userProfileLocalDataSource.upsert(entity)
        return entity.toDomain()
    }

    override suspend fun saveUserLocally(userProfile: UserProfile) {
        userProfileLocalDataSource.upsert(userProfile
            .toEntity(now = System.currentTimeMillis())
        )
    }

    override suspend fun getUserLocally(): UserProfile? {
        return userProfileLocalDataSource.get()?.toDomain()
    }

    override fun observeUser(): Flow<UserProfile?> =
        userProfileLocalDataSource.observe().map { it?.toDomain() }

    override suspend fun refreshUserProfile() {
        val dto = userRemoteDataSource.getUserProfile()
        userProfileLocalDataSource.upsert(dto.toEntity(System.currentTimeMillis()))
    }

    override suspend fun clearLocalUser() {
        userProfileLocalDataSource.clear()
    }
}