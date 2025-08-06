package com.berlin.local.datasource

import com.berlin.local.dao.UserProfileDao
import com.berlin.repository.datasource.local.UserLocalDataSource
import com.berlin.repository.datasource.local.dto.UserProfileEntity
import javax.inject.Inject

class UserLocalDataSourceImp @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserLocalDataSource {
    override suspend fun saveUser(userProfile: UserProfileEntity) {
        userProfileDao.saveUserProfile(userProfile)
    }

    override suspend fun getUser(): UserProfileEntity? {
        return userProfileDao.getUserProfile()
    }

    override suspend fun clear() {
        userProfileDao.clear()
    }
}