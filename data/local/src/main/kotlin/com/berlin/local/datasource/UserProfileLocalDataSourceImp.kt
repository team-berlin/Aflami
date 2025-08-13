package com.berlin.local.datasource

import com.berlin.local.dao.UserProfileDao
import com.berlin.repository.datasource.local.UserProfileLocalDataSource
import com.berlin.repository.datasource.local.dto.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileLocalDataSourceImp @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserProfileLocalDataSource {
    override fun observe(): Flow<UserProfileEntity?> = userProfileDao.observeAccount()

    override suspend fun get(): UserProfileEntity? =userProfileDao.getUserProfile()

    override suspend fun upsert(entity: UserProfileEntity) = userProfileDao.saveUserProfile(entity)

    override suspend fun clear() = userProfileDao.clear()

}