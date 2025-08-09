package com.berlin.repository.datasource.local

import com.berlin.repository.datasource.local.dto.UserProfileEntity

interface UserLocalDataSource {
    suspend fun saveUser(userProfile: UserProfileEntity)
    suspend fun getUser(): UserProfileEntity?
    suspend fun clear()
}