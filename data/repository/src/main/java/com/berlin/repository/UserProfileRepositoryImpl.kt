package com.berlin.repository

import com.berlin.entity.UserProfile
import com.berlin.repository.datasource.local.UserLocalDataSource
import com.berlin.repository.datasource.remote.UserRemoteDataSource
import com.berlin.repository.mapper.toDomain
import com.berlin.repository.mapper.toEntity
import repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserProfileRepository {
    override suspend fun getUserProfile(sessionId: String): UserProfile {
        val dto = userRemoteDataSource.getUserProfile(sessionId)
        val user = dto.toDomain()
        saveUserLocally(user)
        return user
    }

    override suspend fun saveUserLocally(userProfile: UserProfile) {
        userLocalDataSource.saveUser(userProfile.toEntity())
    }

    override suspend fun getUserLocally(): UserProfile? {
        return userLocalDataSource.getUser()?.toDomain()
    }

    override suspend fun refreshUserProfile() {
        TODO("Not yet implemented")
    }

    override suspend fun clearLocalUser() {
        userLocalDataSource.clear()
    }
}